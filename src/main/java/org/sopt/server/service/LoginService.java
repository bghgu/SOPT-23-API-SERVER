package org.sopt.server.service;

import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.Login;
import org.sopt.server.model.Token;

/**
 * Created by ds on 2018-10-23.
 */

public interface LoginService {
    DefaultRes<Token> login(final Login login);
    DefaultRes logout();
}
