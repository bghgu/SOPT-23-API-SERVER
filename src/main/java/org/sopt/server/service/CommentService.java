package org.sopt.server.service;

import org.sopt.server.dto.Comment;
import org.sopt.server.model.DefaultRes;

import java.util.List;

/**
 * Created by ds on 2018-10-23.
 */

public interface CommentService {
    DefaultRes<List<Comment>> findByContentIdx(final int contentIdx);

    DefaultRes<Comment> findByCommentIdx(final int commentIdx);

    DefaultRes save(final Comment comment);

    DefaultRes likes(final int userIdx, final int commentIdx);

    DefaultRes<Comment> update(final Comment comment);

    DefaultRes deleteByCommentIdx(final int commentIdx);

    boolean checkAuth(final int userIdx, final int commentIdx);

    boolean checkLike(final int userIdx, final int commentIdx);
}
