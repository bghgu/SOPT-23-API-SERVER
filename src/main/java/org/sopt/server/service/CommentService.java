package org.sopt.server.service;

import org.sopt.server.dto.Comment;
import org.sopt.server.model.DefaultRes;

import java.util.List;

/**
 * Created by ds on 2018-10-23.
 */

public interface CommentService {
    DefaultRes<List<Comment>> findAll();
    DefaultRes<Comment> findByCommentIdx(final int commentIdx);
    DefaultRes save(final int contentIdx, final Comment comment);
    DefaultRes likes(final int commentIdx);
    DefaultRes<Comment> update(final int commentIdx);
    DefaultRes deleteByCommentIdx(final int commentIdx);
}