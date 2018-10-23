package org.sopt.server.service;

import org.sopt.server.dto.User;
import org.sopt.server.model.DefaultRes;

/**
 * Created by ds on 2018-10-23.
 */

public interface UserService {
    DefaultRes<User> findByUserIdx(final int userIdx);
    DefaultRes save(final User user);
    DefaultRes<User> update(final int userIdx, final User user);
    DefaultRes deleteByUserIdx(final int userIdx);
}
