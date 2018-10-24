package org.sopt.server.service.impl;

import org.sopt.server.dto.User;
import org.sopt.server.mapper.UserMapper;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.SignUpReq;
import org.sopt.server.service.UserService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ds on 2018-10-23.
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 마이페이지 조회
     * @param userIdx
     * @return
     */
    @Override
    public DefaultRes<User> findByUserIdx(final int userIdx) {
        final User user = userMapper.findByUserIdx(userIdx);
        if(user != null) {
            DefaultRes.<User>builder()
                    .responseData(user)
                    .statusCode(StatusCode.NOT_FOUND)
                    .responseMessage(ResponseMessage.NOT_FOUND_USER)
                    .build();
        }
        return DefaultRes.<User>builder()
                .statusCode(StatusCode.NOT_FOUND)
                .responseMessage(ResponseMessage.NOT_FOUND_USER)
                .build();
    }

    @Transactional
    @Override
    public DefaultRes save(final SignUpReq signUpReq) {
        if(signUpReq.checkProperties()) {
            try {
                userMapper.save(signUpReq);
                DefaultRes.builder()
                        .statusCode(StatusCode.CREATED)
                        .responseMessage(ResponseMessage.CREATED_USER)
                        .build();
            }catch (Exception e) {
                DefaultRes.builder()
                        .statusCode(StatusCode.INTERNAL_SERVER_ERROR)
                        .responseMessage(ResponseMessage.INTERNAL_SERVER_ERROR)
                        .build();
            }
        }
        return null;
    }

    @Override
    public DefaultRes<User> update(final int userIdx, final User user) {

        return null;
    }

    @Override
    public DefaultRes deleteByUserIdx(final int userIdx) {
        return null;
    }
}
