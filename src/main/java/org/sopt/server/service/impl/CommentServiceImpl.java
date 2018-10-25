package org.sopt.server.service.impl;

import org.sopt.server.dto.Comment;
import org.sopt.server.mapper.CommentMapper;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.service.CommentService;
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

    @Override
    public DefaultRes<List<Comment>> findAll() {
        return null;
    }

    @Override
    public DefaultRes<Comment> findByCommentIdx(int commentIdx) {
        return null;
    }

    @Override
    public DefaultRes save(int contentIdx, Comment comment) {
        return null;
    }

    @Override
    public DefaultRes likes(int commentIdx) {
        return null;
    }

    @Override
    public DefaultRes<Comment> update(int commentIdx) {
        return null;
    }

    @Override
    public DefaultRes deleteByCommentIdx(int commentIdx) {
        return null;
    }

}
