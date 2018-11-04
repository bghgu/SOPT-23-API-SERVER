package org.sopt.server.service.impl;

import org.sopt.server.dto.Comment;
import org.sopt.server.dto.CommentLike;
import org.sopt.server.dto.Content;
import org.sopt.server.mapper.CommentLikeMapper;
import org.sopt.server.mapper.CommentMapper;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.service.CommentService;
import org.sopt.server.service.ContentService;
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
    private final CommentLikeMapper commentLikeMapper;
    private final ContentService contentService;

    public CommentServiceImpl(
            final CommentMapper commentMapper,
            final CommentLikeMapper commentLikeMapper,
            final ContentService contentService) {
        this.commentMapper = commentMapper;
        this.commentLikeMapper = commentLikeMapper;
        this.contentService = contentService;
    }

    /**
     * 글에 작성된 댓글 조회
     * @param contentIdx 게시글 고유 번호
     * @return 댓글 리스트
     */
    @Override
    public DefaultRes<List<Comment>> findByContentIdx(final int auth, final int contentIdx) {
        List<Comment> commentList = commentMapper.findAllByContentIdx(contentIdx);
        if(commentList.isEmpty()) return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENT);
        if(auth != 0) {
            for(int i = 0; i < commentList.size(); i++) {
                if(commentList.get(i).getU_id() == auth) commentList.get(i).setAuth(true);
            }
        }
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_ALL_COMMENTS, commentList);
    }

    /**
     * 댓글 상세 조회
     * @param commentIdx 댓글 고유 번호
     * @return 댓글
     */
    @Override
    public DefaultRes<Comment> findByCommentIdx(final int auth, final int commentIdx) {
        final Comment comment = commentMapper.findByCommentIdx(commentIdx);
        if(comment == null) return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_COMMENT);
        if(comment.getU_id() == auth) comment.setAuth(true);
        CommentLike commentLike;
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_COMMENT, comment);
    }

    /**
     * 댓글 작성
     * @param contentIdx
     * @param comment
     * @return
     */
    @Override
    public DefaultRes save(final int contentIdx, final Comment comment) {
        if(contentService.findByContentIdx(0, contentIdx).getData() == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);
        comment.setB_id(contentIdx);
        try {
            commentMapper.save(comment);
            return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATE_COMMENT);
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
