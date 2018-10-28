package org.sopt.server.dto;

import lombok.Data;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;

import java.io.Serializable;

/**
 * Created by ds on 2018-10-20.
 */

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -1609275235115313627L;

    private int u_id;
    private String u_name;
    private String u_part;
    private String u_profile;
    private String u_email;
    private boolean auth;

    public DefaultRes<User> res404() {
        return DefaultRes.<User>builder()
                .statusCode(StatusCode.NOT_FOUND)
                .responseMessage(ResponseMessage.NOT_FOUND_USER)
                .build();
    }

    public DefaultRes<User> res200() {
        return DefaultRes.<User>builder()
                .responseData(this)
                .statusCode(StatusCode.OK)
                .responseMessage(ResponseMessage.READ_USER)
                .build();
    }

}
