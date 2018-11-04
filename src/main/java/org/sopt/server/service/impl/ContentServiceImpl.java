package org.sopt.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.Content;
import org.sopt.server.dto.ContentLike;
import org.sopt.server.mapper.CommentMapper;
import org.sopt.server.mapper.ContentLikeMapper;
import org.sopt.server.mapper.ContentMapper;
import org.sopt.server.model.ContentReq;
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
     * 모든 게시글 조회
     *
     * @param pagination
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
        final ContentLike contentLike = contentLikeMapper.findByUserIdxAndContentIdx(auth, content.getB_id());
        if (contentLike != null) content.setLike(true);

        return DefaultRes.res(StatusCode.OK, ResponseMessage.READ_CONTENT, content);
    }

    /**
     * 글 작성
     * 파일 업로드
     *
     * @param contentReq
     * @return DefaultRes
     */
    @Override
    public DefaultRes save(final ContentReq contentReq) {
        if (contentReq.checkProperties()) {
            try {
                if (contentReq.getPhoto() != null)
                    contentReq.setB_photo(fileUploadService.upload(contentReq.getPhoto()));
                contentMapper.save(contentReq);
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

            ContentLike contentLike = contentLikeMapper.findByUserIdxAndContentIdx(auth, contentIdx);
            if (contentLike == null) {
                content.likes();
                contentMapper.like(contentIdx, content.getB_like());
                contentLikeMapper.save(auth, contentIdx);
                content = findByContentIdx(auth, contentIdx).getData();
                return DefaultRes.res(StatusCode.OK, ResponseMessage.LIKE_CONTENT, content);
            } else {
                content.unLikes();
                contentMapper.like(contentIdx, content.getB_like());
                contentLikeMapper.deleteByUserIdxAndContentIdx(auth, contentIdx);
                content = findByContentIdx(auth, contentIdx).getData();
                return DefaultRes.res(StatusCode.OK, ResponseMessage.UNLIKE_COTENT, content);
            }
        }

        return DefaultRes.res(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED, false);
    }

    /**
     * 글 수정
     *
     * @param contentIdx
     * @param contentReq
     * @return DefaultRes
     */
    @Override
    public DefaultRes update(final int contentIdx, final ContentReq contentReq) {
        //글 조회
        Content temp = contentMapper.findByContentIdx(contentIdx);
        if (temp == null)
            return DefaultRes.res(StatusCode.NOT_FOUND, ResponseMessage.NOT_FOUND_CONTENT);

        //권환 확인
        if (temp.getU_id() == contentReq.getU_id()) {

            //수정
            try {
                if (contentReq.getPhoto() != null) temp.setB_photo(fileUploadService.upload(contentReq.getPhoto()));
                temp.update(contentReq);
                contentMapper.updateByContentIdx(temp);
                temp = findByContentIdx(contentReq.getU_id(), contentIdx).getData();
                return DefaultRes.res(StatusCode.OK, ResponseMessage.UPDATE_CONTENT, temp);
            } catch (Exception e) {
                e.printStackTrace();
                return DefaultRes.res(StatusCode.DB_ERROR, ResponseMessage.DB_ERROR);
            }

        }

        return DefaultRes.res(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN, false);
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

        return DefaultRes.res(StatusCode.FORBIDDEN, ResponseMessage.FORBIDDEN, false);
    }
}
