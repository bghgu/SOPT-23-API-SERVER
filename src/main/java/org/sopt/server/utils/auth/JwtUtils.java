package org.sopt.server.utils.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;

import java.util.Optional;

import static com.auth0.jwt.JWT.require;

/**
 * Created by ds on 2018-10-23.
 */

@Slf4j
public class JwtUtils {

    private static final String ISSUER = "DoITSOPT";

    private static final String SECRET = "vji2k@#49c!@!@#$knvldkm3$";

    /**
     * 토큰 생성
     *
     * @param user_idx 토큰에 담길 로그인한 사용자의 회원 고유 IDX
     * @return 토큰
     */
    public static Optional<String> create(final int user_idx) {
        try {
            JWTCreator.Builder b = JWT.create();
            b.withIssuer(ISSUER);
            b.withClaim("user_idx", user_idx);
            return Optional.of(b.sign(Algorithm.HMAC256(SECRET)));
        } catch (JWTCreationException JwtCreationException) {
            log.info(JwtCreationException.getMessage());
        }
        return Optional.empty();
    }

    /**
     * 토큰 해독
     *
     * @param token 토큰
     * @return 로그인한 사용자의 회원 고유 IDX
     */
    public static Optional<Token> decode(final String token) {
        try {
            final JWTVerifier jwtVerifier = require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            //return Optional.ofNullable(new Token(decodedJWT.getClaim("user_idx").asLong().intValue()));
            return Optional.of(new Token(decodedJWT.getClaim("user_idx").asLong().intValue()));
        } catch (JWTVerificationException JwtVerificationException) {
            log.info(JwtVerificationException.getMessage());
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return Optional.empty();
    }

    /**
     * 권환 확인
     *
     * @param jwt
     * @param userIdx
     * @return
     */
    public static DefaultRes<Boolean> checkAuth(final String jwt, final int userIdx) {
        final Optional<JwtUtils.Token> token = decode(jwt);
        if (token.isPresent()) {
            if (userIdx == token.get().getUser_idx())
                return DefaultRes.res(StatusCode.OK, ResponseMessage.AUTHORIZED, true);
            return DefaultRes.res(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN, false);
        }
        return DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED, false);
    }

    public static class Token {
        private int user_idx;

        public Token() {
        }

        public Token(final int user_idx) {
            this.user_idx = user_idx;
        }

        public int getUser_idx() {
            return user_idx;
        }
    }

    public static class TokenRes {
        private String token;

        public TokenRes() {
        }

        public TokenRes(final String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
