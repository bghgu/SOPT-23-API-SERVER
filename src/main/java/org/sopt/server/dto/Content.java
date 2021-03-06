package org.sopt.server.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.sopt.server.model.ContentReq;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by ds on 2018-10-23.
 */

@Data
public class Content implements Serializable {

    private static final long serialVersionUID = -6970879179294995939L;

    private int b_id;
    private String b_title;
    private String b_contents;
    private Date b_date;
    private int u_id;
    private int b_like;
    //private MultipartFile photo;
    private String b_photo;
    private boolean auth;
    private boolean like;

    public void likes() {
        this.b_like++;
    }

    public void unLikes() {
        if(this.b_like > 0) b_like--;
    }

    public boolean checkProperties() {
        if(!existsByTitle()) return false;
        if(!existsByContents()) return false;
        return true;
    }

    public boolean existsByTitle() {
        if(b_title.isEmpty()) return false;
        return true;
    }

    public boolean existsByContents() {
        if(b_contents.isEmpty()) return false;
        return true;
    }

    public void update(final ContentReq contentReq) {
        if(contentReq.getTitle() != null) b_title = contentReq.getTitle();
        if(contentReq.getContents() != null) b_title = contentReq.getContents();
        b_date = new Date();
    }
}
