package org.sopt.server.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ds on 2018-11-04.
 */

@Data
public class ContentReq {

    private String title;
    private String contents;
    private int u_id;
    private MultipartFile photo;
    private String b_photo;

    public boolean checkProperties() {
        if(!existsByTitle()) return false;
        if(!existsByContents()) return false;
        return true;
    }

    public boolean existsByTitle() {
        if(title == null) return false;
        return true;
    }

    public boolean existsByContents() {
        if(contents == null) return false;
        return true;
    }
}
