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
     * 사용자 정보 조회
     *
     * @param userIdx 사용자 고유 번호
     * @return DefaultRes
     */
    @Override
    public DefaultRes findByUserIdx(final int userIdx) {
        //사용자 조회
        final User user = userMapper.findByUserIdx(userIdx);
        if (user != null) {
            return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
        }
        return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
    }

    /**
     * 회원 가입
     * 파일업로드 추가 해야함
     * @param signUpReq 회원 가입 정보
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes save(final SignUpReq signUpReq) {
        //모든 항목이 있는지 검사
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
     * 파일업로드 추가 해야함
     * @param userIdx 사용자 고유 번호
     * @param signUpReq 수정할 데이터
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes<User> update(final int userIdx, final SignUpReq signUpReq) {
        //사용자 조회
        User temp = userMapper.findByUserIdx(userIdx);
        if (temp == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
        //수정할 모든 항목 조회
        if (!signUpReq.checkProperties())
            return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_UPDATE_USER);
        try {
            userMapper.update(userIdx, signUpReq);
            temp = userMapper.findByUserIdx(userIdx);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER, temp);
        } catch (Exception e) {
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 회원 탈퇴
     *
     * @param userIdx 사용자 고유 번호
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes deleteByUserIdx(final int userIdx) {
        //회원 조회
        final User user = userMapper.findByUserIdx(userIdx);
        if (user != null) {
            try {
                userMapper.deleteByUserIdx(userIdx);
                return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.DELETE_USER);
            } catch (Exception e) {
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
    }
}
