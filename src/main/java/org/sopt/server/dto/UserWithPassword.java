package org.sopt.server.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by ds on 2018-11-01.
 */

@Data
public class UserWithPassword implements Serializable {

    private static final long serialVersionUID = -6489730657844661804L;

    private int u_id;
    private String u_name;
    private String u_password;
    private String u_part;
    private String u_profile;
    private String u_email;

}
