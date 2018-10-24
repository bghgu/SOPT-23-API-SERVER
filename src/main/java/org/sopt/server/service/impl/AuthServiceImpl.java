package org.sopt.server.service.impl;

import org.sopt.server.dto.User;
import org.sopt.server.mapper.UserMapper;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.LoginReq;
import org.sopt.server.model.TokenRes;
import org.sopt.server.service.AuthService;
import org.sopt.server.service.JwtService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.springframework.stereotype.Service;

/**
 * Created by ds on 2018-10-23.
 */

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    private final JwtService jwtService;

    public AuthServiceImpl(final UserMapper userMapper, final JwtService jwtService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
    }

    @Override
    public DefaultRes<TokenRes> login(final LoginReq loginReq) {
        final User user = userMapper.findByEmailAndPassword(loginReq.getEmail(), loginReq.getPassword());
        if (user != null) {
            final TokenRes tokenDto = new TokenRes(jwtService.create(user.getU_id()));
            return DefaultRes.<TokenRes>builder()
                    .statusCode(StatusCode.OK)
                    .responseMessage(ResponseMessage.LOGIN_SUCCESS)
                    .responseData(tokenDto)
                    .build();
        }
        return DefaultRes.<TokenRes>builder()
                .statusCode(StatusCode.BAD_REQUEST)
                .responseMessage(ResponseMessage.LOGIN_FAIL)
                .build();
    }

    @Override
    public DefaultRes logout() {
        return null;
    }
}
