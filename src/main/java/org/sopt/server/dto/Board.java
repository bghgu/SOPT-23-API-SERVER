package org.sopt.server.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by ds on 2018-10-23.
 */

@Data
public class Board {
    private int b_id;
    private String b_title;
    private String b_contents;
    private Date b_date;
    private int u_id;
    private int b_like;
    private String b_photo;
}
