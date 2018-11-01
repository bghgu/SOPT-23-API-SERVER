package org.sopt.server.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * Created by ds on 2018-10-24.
 */

@Slf4j
@Data
public class SignUpReq implements Serializable {

    private static final long serialVersionUID = 3382284141255905433L;

    private String name;
    private String password;
    private String part;

    private MultipartFile profile;

    private String profileUrl = "https://s3.ap-northeast-2.amazonaws.com/sopt-23-api-test/Profile-icon-9.png";
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

    public void update(final User user) {
        setName(this.name.equals("") ? user.getU_name() : this.name);
        setPart(this.part.equals("") ? user.getU_part() : this.part);
        //setProfileUrl(this.profile == null ? user.getU_profile() : null);
    }
}
