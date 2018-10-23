package org.sopt.server.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.User;
import org.sopt.server.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ds on 2018-10-23.
 */

@Slf4j
@RestController
public class LoginController {

    private final UserMapper userMapper;

    public LoginController(final UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @GetMapping("")
    public void login() {
        log.info(userMapper.findByUserIdx(1).toString());
    }
}
