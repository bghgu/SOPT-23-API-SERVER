package org.sopt.server.service;

import org.sopt.server.dto.User;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.SignUpReq;

/**
 * Created by ds on 2018-10-23.
 */

public interface UserService {
    DefaultRes<User> findByUserIdx(final int userIdx);

    DefaultRes save(final SignUpReq signUpReq);

    DefaultRes<User> update(final int userIdx, final SignUpReq signUpReq);

    DefaultRes deleteByUserIdx(final int userIdx);
}
