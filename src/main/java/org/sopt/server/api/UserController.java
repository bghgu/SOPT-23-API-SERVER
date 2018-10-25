package org.sopt.server.api;

import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.SignUpReq;
import org.sopt.server.service.UserService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.sopt.server.utils.auth.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ds on 2018-10-23.
 */

@RestController
@RequestMapping("/users")
public class UserController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final UserService userService;

    public UserController(final UserService userService) {
        this.userService = userService;
    }

    /**
     * 마이페이지 조회
     * @param userIdx
     * @return
     */
    @GetMapping("/{userIdx}")
    public ResponseEntity getMyPage(@PathVariable final int userIdx) {
        try{
            return new ResponseEntity<>(userService.findByUserIdx(userIdx), HttpStatus.OK);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 가입
     * @param signUpReq
     * @return
     */
    @PostMapping("")
    public ResponseEntity signUp(@RequestBody final SignUpReq signUpReq) {
        try{
            return new ResponseEntity<>(userService.save(signUpReq), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 내 정보 수정
     * @param userIdx
     * @return
     */
    @Auth
    @PutMapping("/{userIdx}")
    public ResponseEntity updateMyPage(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int userIdx,
            @RequestBody final SignUpReq signUpReq) {
        try{
            return new ResponseEntity<>(userService.update(userIdx, signUpReq) , HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 회원 탈퇴
     * @param userIdx
     * @return
     */
    @Auth
    @DeleteMapping("/{userIdx}")
    public ResponseEntity goodBye(@PathVariable final int userIdx) {
        try{
            return new ResponseEntity<>(userService.deleteByUserIdx(userIdx), HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
