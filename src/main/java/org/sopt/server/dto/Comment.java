package org.sopt.server.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ds on 2018-10-23.
 */

@Data
@Alias(value = "comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = -376999960591431403L;

    private int c_id;
    private int b_id;
    private int u_id;
    private String c_contents;
    private Date c_date;
}
