package org.sopt.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ds on 2018-10-28.
 */

@Data
public class ContentLike implements Serializable {
    private static final long serialVersionUID = -7527777480038198932L;

    private int u_id;
    private int b_id;
}
