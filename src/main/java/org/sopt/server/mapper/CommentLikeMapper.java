package org.sopt.server.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.server.dto.CommentLike;
import org.sopt.server.dto.ContentLike;

/**
 * Created by ds on 2018-10-28.
 */

@Mapper
public interface CommentLikeMapper {

    @Select("SELECT * FROM comment_like WHERE u_id = #{u_id} AND c_id = #{c_id}")
    CommentLike findByUserIdxAndCommentIdx(@Param("u_id") final int userIdx, @Param("c_id") final int commentIdx);

    @Insert("INSERT INTO comment_like(u_id, c_id) VALUES(#{u_id}, #{c_id})")
    void save(@Param("u_id") final int userIdx, @Param("c_id") final int commentIdx);

    @Delete("DELETE FROM comment_like WHERE u_id = #{u_id} AND c_id = #{c_id}")
    void deleteByUserIdxAndCommentIdx(@Param("u_id") final int userIdx, @Param("c_id") final int commentIdx);
}
