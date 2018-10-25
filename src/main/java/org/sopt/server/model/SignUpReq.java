package org.sopt.server.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ds on 2018-10-24.
 */

@Data
public class SignUpReq {

    private String name;
    private String password;
    private String part;
    private MultipartFile profile;
    private String profileUrl;
    private String email;

    public boolean checkProperties() {
        if(!existsByEmail()) return false;
        if(!existsByPassword()) return false;
        if(!existsByPart()) return false;
        if(!existsByName()) return false;
        return true;
    }

    public boolean existsByName() {
        if(name == null) return false;
        return true;
    }

    public boolean existsByPassword() {
        if(password == null) return false;
        return true;
    }

    public boolean existsByPart() {
        if(part == null) return false;
        return true;
    }

    public boolean existsByEmail() {
        if(email == null) return false;
        return true;
    }

}
