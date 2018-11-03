package org.sopt.server.model;

import lombok.Data;

/**
 * Created by ds on 2018-10-23.
 */

@Data
public class Pagination {

    private int offset = 0;
    private int limit = 10;
    private String keyword = "";

}
