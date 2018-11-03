package org.sopt.server.dto;

import lombok.Data;
import org.sopt.server.model.SignUpReq;

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

    public void update(final SignUpReq signUpReq) {
        if(signUpReq.getName() != null) u_name = signUpReq.getName();
        if(signUpReq.getPart() != null) u_part = signUpReq.getPart();
    }
}
