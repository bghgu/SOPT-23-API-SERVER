package org.sopt.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.Content;
import org.sopt.server.dto.ContentLike;
import org.sopt.server.mapper.ContentLikeMapper;
import org.sopt.server.mapper.ContentMapper;
import org.sopt.server.model.ContentReq;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.Pagination;
import org.sopt.server.service.ContentService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * Created by ds on 2018-10-23.
 */

@Slf4j
@Service
public class ContentServiceImpl implements ContentService {

    private final ContentMapper contentMapper;
    private final ContentLikeMapper contentLikeMapper;
    private final FileUploadService fileUploadService;

    public ContentServiceImpl(final ContentMapper contentMapper, final FileUploadService fileUploadService, final ContentLikeMapper contentLikeMapper) {
        this.contentMapper = contentMapper;
        this.fileUploadService = fileUploadService;
        this.contentLikeMapper = contentLikeMapper;
    }

    /**
     * 모든 게시글 조회
     *
     * @param pagination 페이지네이션
     * @return DefaultRes
     */
    @Override
    public DefaultRes<List<Content>> findAll(final Pagination pagination) {
        final List<Content> contentList = contentMapper.findAll(pagination);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_ALL_CONTENTS, contentList);
    }

    /**
     * 글 상세 조회
     *
     * @param contentIdx 글 고유 번호
     * @return DefaultRes
     */
    @Override
    public DefaultRes<Content> findByContentIdx(final int contentIdx) {
        //글 조회
        Content content = contentMapper.findByContentIdx(contentIdx);
        if (content == null) return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CONTENT, content);
    }

    /**
     * 글 작성
     *
     * @param contentReq 글 내용
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes save(final ContentReq contentReq) {
        if (contentReq.checkProperties()) {
            try {
                if (contentReq.getPhoto() != null)
                    contentReq.setB_photo(fileUploadService.upload(contentReq.getPhoto()));
                contentMapper.save(contentReq);
                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATE_CONTENT);
            } catch (Exception e) {
                log.info(e.getMessage());
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_CONTENT);
    }

    /**
     * 글 좋아요
     *
     * @param userIdx    사용자 고유 번호
     * @param contentIdx 글 고유 번호
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes likes(final int userIdx, final int contentIdx) {
        //글 조회
        Content content = findByContentIdx(contentIdx).getData();
        if (content == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);

        ContentLike contentLike = contentLikeMapper.findByUserIdxAndContentIdx(userIdx, contentIdx);

        try {
            if (contentLike == null) {
                content.likes();
                contentMapper.like(contentIdx, content.getB_like());
                contentLikeMapper.save(userIdx, contentIdx);
            } else {
                content.unLikes();
                contentMapper.like(contentIdx, content.getB_like());
                contentLikeMapper.deleteByUserIdxAndContentIdx(userIdx, contentIdx);
            }

            content = findByContentIdx(contentIdx).getData();
            content.setAuth(checkAuth(userIdx, contentIdx));

            return DefaultRes.res(StatusCode.OK, ResponseMessage.LIKE_CONTENT, content);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 글 수정
     *
     * @param contentIdx 글 고유 번호
     * @param contentReq 수정할 글
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes update(final int contentIdx, final ContentReq contentReq) {
        //글 조회
        Content content = findByContentIdx(contentIdx).getData();
        if (content == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);

        //수정
        try {
            if (contentReq.getPhoto() != null) content.setB_photo(fileUploadService.upload(contentReq.getPhoto()));
            content.update(contentReq);
            contentMapper.updateByContentIdx(content);

            content = findByContentIdx(contentIdx).getData();
            content.setAuth(checkLike(contentReq.getU_id(), contentIdx));

            return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_CONTENT, content);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 글 삭제
     *
     * @param contentIdx 글 고유 번호
     * @return DefaultRes
     */
    @Transactional
    @Override
    public DefaultRes deleteByContentIdx(final int contentIdx) {
        //글 조회
        final Content content = findByContentIdx(contentIdx).getData();
        if (content == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);

        //삭제
        try {
            contentMapper.deleteByContentIdx(contentIdx);
            return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.DELETE_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
        }
    }

    /**
     * 글 권환 확인
     *
     * @param userIdx    사용자 고유 번호
     * @param contentIdx 글 고유 번호
     * @return boolean
     */
    @Override
    public boolean checkAuth(final int userIdx, final int contentIdx) {
        return userIdx == findByContentIdx(contentIdx).getData().getU_id();
    }

    /**
     * 좋아요 여부 확인
     *
     * @param userIdx    사용자 고유 번호
     * @param contentIdx 글 고유 번호
     * @return boolean
     */
    @Override
    public boolean checkLike(final int userIdx, final int contentIdx) {
        return contentLikeMapper.findByUserIdxAndContentIdx(userIdx, contentIdx) != null;
    }
}
