package org.sopt.server.service.impl;

import org.sopt.server.dto.User;
import org.sopt.server.mapper.UserMapper;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.LoginReq;
import org.sopt.server.service.AuthService;
import org.sopt.server.utils.auth.JwtUtils;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.springframework.stereotype.Service;

/**
 * Created by ds on 2018-10-23.
 */

@Service
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;

    public AuthServiceImpl(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public DefaultRes<JwtUtils.TokenRes> login(final LoginReq loginReq) {
        final User user = userMapper.findByEmailAndPassword(loginReq.getEmail(), loginReq.getPassword());
        if (user != null) {
            final JwtUtils.TokenRes tokenDto = new JwtUtils.TokenRes(JwtUtils.create(user.getU_id()).get());
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, tokenDto);
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.LOGIN_FAIL);
    }

    @Override
    public DefaultRes logout() {
        return null;
    }
}
