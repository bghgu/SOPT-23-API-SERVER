package org.sopt.server.mapper;

import org.apache.ibatis.annotations.*;
import org.sopt.server.dto.Content;
import org.sopt.server.model.ContentReq;
import org.sopt.server.model.Pagination;
import org.sopt.server.model.SignUpReq;

import java.util.List;

/**
 * Created by ds on 2018-10-23.
 */

@Mapper
public interface ContentMapper {

    /**
     * 글 전체 조회
     * @return
     */
    @Select("SELECT * FROM board ORDER BY b_id DESC LIMIT #{limit} OFFSET #{offset}")
    List<Content> findAll(final Pagination pagination);

    /**
     * 글 상세 조회
     * @param contentIdx
     * @return
     */
    @Select("SELECT * FROM board WHERE b_id = #{b_id}")
    Content findByContentIdx(@Param("b_id") final int contentIdx);

    /**
     * 글 작성
     * @param contentReq
     */
    @Insert("INSERT INTO board(b_title, b_contents, u_id, b_photo) VALUES(#{title}, #{contents}, #{u_id}, #{b_photo})")
    @Options(useGeneratedKeys = true, keyProperty = "b_id")
    void save(final ContentReq contentReq);

    /**
     * 글 좋아요
     * @param contentIdx
     * @param b_like
     * @return
     */
    @Update("UPDATE board SET b_like = #{b_like} WHERE b_id = #{b_id}")
    void like(@Param("b_id") final int contentIdx, @Param("b_like") final int b_like);

    /**
     * 글 수정
     * @param content
     */
    @Update("UPDATE board SET b_title = #{b_title}, b_contents = #{b_contents}, b_photo = #{b_photo}, b_date = #{b_date} WHERE b_id = #{b_id}")
    void updateByContentIdx(final Content content);

    /**
     * 글 삭제
     * @param contentIdx
     */
    @Delete("DELETE FROM board WHERE b_id = #{b_id}")
    void deleteByContentIdx(@Param("b_id") final int contentIdx);
}
