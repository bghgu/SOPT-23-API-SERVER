package org.sopt.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ds on 2018-10-28.
 */

@Data
public class CommentLike implements Serializable {
    private static final long serialVersionUID = -2465473319680239056L;

    private int u_id;
    private int c_id;
}
