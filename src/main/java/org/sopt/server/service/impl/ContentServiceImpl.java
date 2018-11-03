package org.sopt.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.Content;
import org.sopt.server.dto.ContentLike;
import org.sopt.server.mapper.CommentMapper;
import org.sopt.server.mapper.ContentLikeMapper;
import org.sopt.server.mapper.ContentMapper;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.Pagination;
import org.sopt.server.service.CommentService;
import org.sopt.server.service.ContentService;
import org.sopt.server.service.FileUploadService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.springframework.stereotype.Service;

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

    public ContentServiceImpl(
            final ContentMapper contentMapper,
            final FileUploadService fileUploadService,
            final ContentLikeMapper contentLikeMapper) {
        this.contentMapper = contentMapper;
        this.fileUploadService = fileUploadService;
        this.contentLikeMapper = contentLikeMapper;
    }

    /**
     * 모든 글 조회
     * 페이지 네이션 추가
     *
     * @return
     */
    @Override
    public DefaultRes<List<Content>> findAll(final Pagination pagination) {
        final List<Content> contentList = contentMapper.findAll(pagination);
        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_ALL_CONTENTS, contentList);
    }

    /**
     * 글 상세 조회
     *
     * @param contentIdx
     * @return DefaultRes
     */
    @Override
    public DefaultRes<Content> findByContentIdx(final int auth, final int contentIdx) {
        //글 조회
        Content content = contentMapper.findByContentIdx(contentIdx);
        if (content == null) return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);

        //내글 조회
        if (auth == content.getU_id()) content.setAuth(true);

        //좋아요 여부 확인
        final ContentLike contentLike = contentLikeMapper.getLike(auth, content.getB_id());
        if(contentLike != null) content.setLike(true);

        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CONTENT, content);
    }

    /**
     * 글 작성
     * 파일 업로드
     *
     * @param content
     * @return DefaultRes
     */
    @Override
    public DefaultRes save(final Content content) {
        if (content.checkProperties()) {
            try {
                contentMapper.save(content);
                return DefaultRes.res(StatusCode.CREATED, ResponseMessage.CREATE_CONTENT);
            } catch (Exception e) {
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }
        return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_CREATE_CONTENT);
    }

    /**
     * 글 좋아요
     * 좋아요 체크 추가
     * 안좋아요
     *
     * @param contentIdx
     * @return DefaultRes
     */
    @Override
    public DefaultRes likes(final int auth, final int contentIdx) {
        //글 조회
        Content content = contentMapper.findByContentIdx(contentIdx);
        if (content == null) return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);

        if (auth == content.getU_id()) {
            content.likes();
            contentMapper.like(contentIdx, content);
            return DefaultRes.res(StatusCode.OK, ResponseMessage.LIKE_CONTENT, content);
        }

        return DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED, false);
    }

    /**
     * 글 수정
     *
     * @param contentIdx
     * @param content
     * @return DefaultRes
     */
    @Override
    public DefaultRes update(final int contentIdx, final Content content) {
        //글 조회
        Content temp = contentMapper.findByContentIdx(contentIdx);
        if (temp == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);

        //권환 확인
        if (temp.getU_id() == content.getU_id()) {

            //항목 확인
            if (!content.checkProperties())
                return DefaultRes.res(StatusCode.BAD_REQUEST, ResponseMessage.FAIL_UPDATE_CONTENT);

            //수정
            try {
                contentMapper.updateByContentIdx(content, contentIdx);
                temp = contentMapper.findByContentIdx(contentIdx);
                return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_CONTENT, temp);
            } catch (Exception e) {
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }

        }

        return DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED, false);
    }

    /**
     * 글 삭제
     *
     * @param contentIdx 글 고유 번호
     * @return DefaultRes
     */
    @Override
    public DefaultRes deleteByContentIdx(final int auth, final int contentIdx) {
        //글 조회
        final Content content = contentMapper.findByContentIdx(contentIdx);
        if (content == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);

        //내 글인지 확인
        if (auth == content.getU_id()) {

            //삭제
            try {
                contentMapper.deleteByContentIdx(contentIdx);
                return DefaultRes.res(StatusCode.NO_CONTENT, ResponseMessage.DELETE_CONTENT);
            } catch (Exception e) {
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }
        }

        return DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED, false);
    }
}
