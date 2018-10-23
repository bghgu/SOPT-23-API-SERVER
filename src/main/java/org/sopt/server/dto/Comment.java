package org.sopt.server.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by ds on 2018-10-23.
 */

@Data
public class Comment {
    private int c_id;
    private int b_id;
    private int u_id;
    private String c_contents;
    private Date c_date;
}
