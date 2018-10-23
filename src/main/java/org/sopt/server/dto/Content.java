package org.sopt.server.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ds on 2018-10-23.
 */

@Data
@Alias(value = "board")
public class Content implements Serializable {

    private static final long serialVersionUID = -6970879179294995939L;

    private int b_id;
    private String b_title;
    private String b_contents;
    private Date b_date;
    private int u_id;
    private int b_like;
    private String b_photo;
}
