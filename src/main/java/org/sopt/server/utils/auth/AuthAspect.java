package org.sopt.server.utils.auth;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.sopt.server.dto.User;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.service.UserService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Created by ds on 2018-10-23.
 */

@Component
@Aspect
public class AuthAspect {

    private final static String AUTHORIZATION = "Authorization";

    /**
     * 실패 시 기본 반환 Response
     */
    private final static DefaultRes DEFAULT_RES = DefaultRes.builder().statusCode(StatusCode.UNAUTHORIZED).responseMessage(ResponseMessage.UNAUTHORIZED).build();
    private final static ResponseEntity<DefaultRes> RES_RESPONSE_ENTITY = new ResponseEntity<>(DEFAULT_RES, HttpStatus.UNAUTHORIZED);

    private final HttpServletRequest httpServletRequest;

    private final UserService userService;

    /**
     * Repository 의존성 주입
     */
    public AuthAspect(final HttpServletRequest httpServletRequest, final UserService userService) {
        this.httpServletRequest = httpServletRequest;
        this.userService = userService;
    }

    @Around("@annotation(org.sopt.server.utils.auth.Auth)")
    public Object around(final ProceedingJoinPoint pjp) throws Throwable {
        final String jwt = httpServletRequest.getHeader(AUTHORIZATION);

        //토큰 존재 여부 확인
        if (jwt == null) {
            return RES_RESPONSE_ENTITY;
        }

        //토큰 해독
        final Optional<JwtUtils.Token> token = JwtUtils.decode(jwt);

        //토큰 검사
        if (token.isPresent()) {
            return RES_RESPONSE_ENTITY;
        } else {
            final User user = userService.findByUserIdx(token.get().getUser_idx()).getResponseData();

            //유효 사용자 검사
            if (user == null) {
                return RES_RESPONSE_ENTITY;
            }

            return pjp.proceed(pjp.getArgs());
        }

    }
}
