package org.sopt.server.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by ds on 2018-11-02.
 */

@Data
public class test {
    private String name;
    private MultipartFile profile;
}
