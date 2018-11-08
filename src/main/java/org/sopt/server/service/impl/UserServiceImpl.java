package org.sopt.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.User;
import org.sopt.server.mapper.UserMapper;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.SignUpReq;
import org.sopt.server.service.FileUploadService;
import org.sopt.server.service.UserService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Created by ds on 2018-10-23.
 */

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final FileUploadService fileUploadService;

    public UserServiceImpl(final UserMapper userMapper, final FileUploadService fileUploadService) {
        this.userMapper = userMapper;
        this.fileUploadService = fileUploadService;
    }

    /**
     * 사용자 정보 조회
     *
     * @param userIdx 사용자 고유 번호
     * @return DefaultRes
     */
    @Override
    public DefaultRes<User> findByUserIdx(final int userIdx) {
        //사용자 조회
        User user = userMapper.findByUserIdx(userIdx);
        if (user != null) return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_USER, user);
        return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
    }

    /**
     * 회원 가입
     *
     * @param signUpReq 회원 가입 정보
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes save(final SignUpReq signUpReq) {
        //모든 항목이 있는지 검사
        if (signUpReq.checkProperties()) {
            final User user = userMapper.findByEmail(signUpReq.getEmail());
            if (user == null) {
                try {
                    if (signUpReq.getProfile() != null)
                        signUpReq.setProfileUrl(fileUploadService.upload(signUpReq.getProfile()));
                    userMapper.save(signUpReq);
                    return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATED_USER);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
                }
            } else return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.ALREADY_USER);
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_USER);
    }


    /**
     * 회원 정보 수정
     *
     * @param userIdx   사용자 고유 번호
     * @param signUpReq 수정할 데이터
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes<User> update(final int userIdx, SignUpReq signUpReq) {
        //사용자 조회
        User temp = findByUserIdx(userIdx).getData();
        if (temp == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);

        try {
            temp.update(signUpReq);
            if (signUpReq.getProfile() != null) temp.setU_profile(fileUploadService.upload(signUpReq.getProfile()));

            userMapper.update(temp);

            temp = findByUserIdx(userIdx).getData();
            temp.setAuth(true);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_USER, temp);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
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
                log.error(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_USER);
    }
}
