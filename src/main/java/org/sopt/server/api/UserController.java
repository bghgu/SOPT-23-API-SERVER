package org.sopt.server.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.User;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.SignUpReq;
import org.sopt.server.service.UserService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.sopt.server.utils.auth.Auth;
import org.sopt.server.utils.auth.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ds on 2018-10-23.
 */

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private static final String HEADER = "Authorization";

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
    private static final DefaultRes UNAUTHORIZED_RES = new DefaultRes(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * 마이 페이지 조회
     *
     * @param httpServletRequest Request
     * @param userIdx 사용자 고유 번호
     * @return ResponseEntity
     */
    @GetMapping("/{userIdx}")
    public ResponseEntity getMyPage(final HttpServletRequest httpServletRequest, @PathVariable final int userIdx) {
        try {
            final int tokenValue = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_idx();
            DefaultRes<User> defaultRes = userService.findByUserIdx(userIdx);
            if (tokenValue == userIdx) defaultRes.getData().setAuth(true);
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 가입
     *
     * @param signUpReq 회원 정보
     * @return ResponseEntity
     */
    @PostMapping("")
    public ResponseEntity signUp(@RequestBody final SignUpReq signUpReq) {
        try {
            return new ResponseEntity<>(userService.save(signUpReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 정보 수정
     *
     * @param jwt       토큰
     * @param userIdx   사용자 고유 번호
     * @param signUpReq 회원 내용
     * @param profile   사진
     * @return ResponseEntity
     */
    @Auth
    @PutMapping("/{userIdx}")
    public ResponseEntity updateMyPage(@RequestHeader("Authorization") final String jwt, @PathVariable final int userIdx,
                                       final SignUpReq signUpReq,
                                       @RequestPart(value = "profile", required = false) final MultipartFile profile) {
        try {
            if (profile != null) signUpReq.setProfile(profile);
            final int tokenValue = JwtUtils.decode(jwt).getUser_idx();
            if (tokenValue == userIdx)
                return new ResponseEntity<>(userService.update(userIdx, signUpReq), HttpStatus.OK);
            return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 탈퇴
     *
     * @param jwt     토큰
     * @param userIdx 사용자 고유 번호
     * @return ResponseEntity
     */
    @Auth
    @DeleteMapping("/{userIdx}")
    public ResponseEntity goodBye(@RequestHeader("Authorization") final String jwt, @PathVariable final int userIdx) {
        try {
            final int tokenValue = JwtUtils.decode(jwt).getUser_idx();
            if (tokenValue == userIdx) return new ResponseEntity<>(userService.deleteByUserIdx(userIdx), HttpStatus.OK);
            return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
