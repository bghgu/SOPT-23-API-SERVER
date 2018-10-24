package org.sopt.server.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.server.dto.Comment;
import org.sopt.server.dto.Content;

import java.util.List;

/**
 * Created by ds on 2018-10-23.
 */

@Mapper
public interface CommentMapper {

    /**
     * 해당 게시글의 모든 댓글 조회
     * @param contentIdx
     * @return
     */
    @Select("SELECT * FROM comment WHERE b_id = #{b_id}")
    List<Comment> findAllByContentIdx(@Param("b_id") final int contentIdx);

    /**
     * 댓글 조회
     * @param commentIdx
     * @return
     */
    @Select("SELECT * FROM comment WHERE c_id = #{c_id}")
    Comment findByCommentIdx(@Param("c_id") final int commentIdx);

    /**
     * 댓글 작성
     * @param comment
     */
    @Insert("INSERT INTO comment() VALUES()")
    @Options(useGeneratedKeys = true, keyProperty = "c_id")
    void save(final Comment comment);

    /**
     * 댓글 좋아요
     * @param likeCounts
     * @param commentIdx
     */
    @Update("UPDATE comment SET c_lie = #{c_like} where c_id = #{c_id}")
    void like(@Param("c_like") final int likeCounts, @Param("c_id") final int commentIdx);

    /**
     * 댓글 수정
     * @param content
     * @param commentIdx
     */
    @Update("UPDATE comment SET() WHERE c_id = #{c_id}")
    void updateByCommentIdx(final Content content, @Param("c_id") final int commentIdx);

    //댓글 삭제
    @Delete("DELETE FROM comment WHERE c_id = #{c_id}")
    void deleteByConmmentIdx(@Param("c_id") final int commentIdx);
}
