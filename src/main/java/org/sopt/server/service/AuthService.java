package org.sopt.server.service;

import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.LoginReq;
import org.sopt.server.utils.auth.JwtUtils;

/**
 * Created by ds on 2018-10-23.
 */

public interface AuthService {
    DefaultRes<JwtUtils.TokenRes> login(final LoginReq loginReq);
}
