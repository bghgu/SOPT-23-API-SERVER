package org.sopt.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.sopt.server.dto.User;

/**
 * Created by ds on 2018-10-20.
 */

@Mapper
public interface UserMapper {
    User findByUserIdx(final int userIdx);
}
