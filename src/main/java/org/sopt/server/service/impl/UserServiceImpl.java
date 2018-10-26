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
    public DefaultRes findByUserIdx(final int userIdx) {
        final User user = userMapper.findByUserIdx(userIdx);
        if (user != null) {
            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
        }
        return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
    }

    /**
     * 회원 가입
     * @param signUpReq
     * @return
     */
    @Transactional
    @Override
    public DefaultRes save(final SignUpReq signUpReq) {
        if (signUpReq.checkProperties()) {
            try {
                userMapper.save(signUpReq);
                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_USER);
            } catch (Exception e) {
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_UPDATE_USER);
    }


    /**
     * 회원 정보 수정
     * @param userIdx
     * @param signUpReq
     * @return
     */
    @Transactional
    @Override
    public DefaultRes<User> update(final int userIdx, final SignUpReq signUpReq) {
        final User temp = userMapper.findByUserIdx(userIdx);
        if (temp == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        if (!signUpReq.checkProperties())
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_UPDATE_USER);
        try {
            userMapper.save(signUpReq);
            final User user = userMapper.findByUserIdx(userIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER, user);
        } catch (Exception e) {
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 회원 탈퇴
     * @param userIdx
     * @return
     */
    @Transactional
    @Override
    public DefaultRes deleteByUserIdx(final int userIdx) {
        final User user = userMapper.findByUserIdx(userIdx);
        if (user != null) {
            try {
                userMapper.deleteByUserIdx(userIdx);
                return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.DELETE_USER);
            }catch (Exception e) {
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
    }
}
