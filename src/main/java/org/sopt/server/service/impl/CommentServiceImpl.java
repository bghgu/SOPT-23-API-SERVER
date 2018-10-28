package org.sopt.server.service.impl;

import org.sopt.server.dto.Comment;
import org.sopt.server.mapper.CommentMapper;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.service.CommentService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ds on 2018-10-23.
 */

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    public CommentServiceImpl(final CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    /**
     * 글에 작성된 댓글 조회
     * @param contentIdx
     * @return
     */
    @Override
    public DefaultRes<List<Comment>> findByContentIdx(final int contentIdx) {
        final List<Comment> commentList = commentMapper.findAllByContentIdx(contentIdx);
        if(commentList.isEmpty()) return DefaultRes.res(StatusCode.NOT_FOUND, "");
        return DefaultRes.res(StatusCode.OK, "", commentList);
    }

    /**
     *
     * @param commentIdx
     * @return
     */
    @Override
    public DefaultRes<Comment> findByCommentIdx(final int commentIdx) {
        final Comment comment = commentMapper.findByCommentIdx(commentIdx);
        if(comment == null) return DefaultRes.res(StatusCode.NOT_FOUND, "");
        return DefaultRes.res(StatusCode.OK, "", comment);
    }

    /**
     *
     * @param contentIdx
     * @param comment
     * @return
     */
    @Override
    public DefaultRes save(final int contentIdx, final Comment comment) {
        comment.setB_id(contentIdx);
        try {
            commentMapper.save(comment);
            return DefaultRes.res(StatusCode.CREATED, "");
        }catch (Exception e) {
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     *
     * @param commentIdx
     * @return
     */
    @Override
    public DefaultRes likes(final int commentIdx) {
        Comment comment = commentMapper.findByCommentIdx(commentIdx);
        comment.likes();
        try {
            commentMapper.like(commentIdx, comment);
            comment = commentMapper.findByCommentIdx(commentIdx);
            return DefaultRes.res(StatusCode.OK, "", comment);
        }catch (Exception e) {
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     *
     * @param commentIdx
     * @return
     */
    @Override
    public DefaultRes<Comment> update(final int commentIdx, final Comment comment) {
        Comment temp = commentMapper.findByCommentIdx(commentIdx);
        if(temp == null) return DefaultRes.res(StatusCode.NOT_FOUND, "");
        try {
            commentMapper.updateByCommentIdx(commentIdx, comment);
            temp = commentMapper.findByCommentIdx(commentIdx);
            return DefaultRes.res(StatusCode.OK, "", temp);
        }catch (Exception e) {
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     *
     * @param commentIdx
     * @return
     */
    @Override
    public DefaultRes deleteByCommentIdx(final int commentIdx) {
        final Comment comment = commentMapper.findByCommentIdx(commentIdx);
        if(comment == null) return DefaultRes.res(StatusCode.NOT_FOUND, "");
        try {
            commentMapper.deleteByConmmentIdx(commentIdx);
            return DefaultRes.res(StatusCode.NO_CONTENT, "");
        }catch (Exception e) {
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

}
