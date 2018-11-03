package org.sopt.server.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.SignUpReq;
import org.sopt.server.model.test;
import org.sopt.server.service.UserService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.sopt.server.utils.auth.Auth;
import org.sopt.server.utils.auth.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by ds on 2018-10-23.
 */

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private static final String HEADER = "Authorization";

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * 마이 페이지 조회
     * @param httpServletRequest
     * @param userIdx
     * @return
     */
    @GetMapping("/{userIdx}")
    public ResponseEntity getMyPage(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int userIdx
    ) {
        try {
            final String jwt = httpServletRequest.getHeader(HEADER);
            if (JwtUtils.checkAuth(jwt, userIdx).getData())
                return new ResponseEntity<>(userService.findByUserIdx(JwtUtils.decode(jwt).get().getUser_idx(), userIdx), HttpStatus.OK);
            return new ResponseEntity<>(userService.findByUserIdx(0, userIdx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 가입
     *
     * @param
     * @return
     */
    @PostMapping("")
    public ResponseEntity signUp(final SignUpReq signUpReq) throws IOException {
        try {
            return new ResponseEntity<>(userService.save(signUpReq), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 내 정보 수정
     *
     * @param userIdx
     * @return
     */
    @Auth
    @PutMapping("/{userIdx}")
    public ResponseEntity updateMyPage(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int userIdx,
            final SignUpReq signUpReq,
            @RequestPart final MultipartFile profile) throws IOException {
        try {
            if(profile != null) signUpReq.setProfile(profile);
            final DefaultRes<Boolean> defaultRes = JwtUtils.checkAuth(jwt, userIdx);
            if (defaultRes.getData())
                return new ResponseEntity<>(userService.update(userIdx, signUpReq), HttpStatus.OK);
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 탈퇴
     *
     * @param userIdx
     * @return
     */
    @Auth
    @DeleteMapping("/{userIdx}")
    public ResponseEntity goodBye(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int userIdx) {
        try {
            final DefaultRes<Boolean> defaultRes = JwtUtils.checkAuth(jwt, userIdx);
            if (defaultRes.getData())
                return new ResponseEntity<>(userService.deleteByUserIdx(userIdx), HttpStatus.OK);
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
