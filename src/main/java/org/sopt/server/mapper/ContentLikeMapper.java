package org.sopt.server.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.server.dto.ContentLike;

/**
 * Created by ds on 2018-10-28.
 */

@Mapper
public interface ContentLikeMapper {

    @Select("SELECT * FROM board_like WHERE u_id = #{u_id} AND b_id = #{b_id}")
    ContentLike findByUserIdxAndContentIdx(@Param("u_id") final int userIdx, @Param("b_id") final int contentIdx);

    @Insert("INSERT INTO board_like(u_id, b_id) VALUES(#{u_id}, #{b_id})")
    void save(@Param("u_id") final int userIdx, @Param("b_id") final int contentIdx);

    @Delete("DELETE FROM board_like WHERE u_id = #{u_id} AND b_id = #{b_id}")
    void deleteByUserIdxAndContentIdx(@Param("u_id") final int userIdx, @Param("b_id") final int contentIdx);

}
