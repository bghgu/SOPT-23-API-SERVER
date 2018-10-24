package org.sopt.server.model;

/**
 * Created by ds on 2018-10-24.
 */

public class Token {
    private int user_idx;

    public Token() {
    }

    public Token(final int user_idx) {
        this.user_idx = user_idx;
    }

    public int getUser_idx() {
        return user_idx;
    }
}

