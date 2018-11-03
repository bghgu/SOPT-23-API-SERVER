package org.sopt.server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.sopt.server.dto.ContentLike;

/**
 * Created by ds on 2018-10-28.
 */

@Mapper
public interface ContentLikeMapper {

    @Select("SELECT * FROM board_like WHERE u_id = #{u_id} AND b_id = #{b_id}")
    ContentLike getLike(@Param("u_id") final int u_id, @Param("b_id") final int b_id);
}
