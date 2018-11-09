package org.sopt.server.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.Content;
import org.sopt.server.model.ContentReq;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.model.Pagination;
import org.sopt.server.service.ContentService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.sopt.server.utils.auth.Auth;
import org.sopt.server.utils.auth.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ds on 2018-10-23.
 */

@Slf4j
@RestController
@RequestMapping("/contents")
public class ContentController {

    private static final String AUTHORIZATION = "Authorization";

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
    private static final DefaultRes UNAUTHORIZED_RES = new DefaultRes(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED);

    private final ContentService contentService;

    public ContentController(final ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * 모든 게시글 조회
     *
     * @param pagination 페이지네이션
     * @return ResponseEntity
     */
    @GetMapping("")
    public ResponseEntity getAllContents(final Pagination pagination) {
        try {
            return new ResponseEntity<>(contentService.findAll(pagination), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 게시글 조회
     *
     * @param httpServletRequest Request
     * @param contentIdx         글 고유 번호
     * @return ResponseEntity
     */
    @GetMapping("/{contentIdx}")
    public ResponseEntity getContents(final HttpServletRequest httpServletRequest,
                                      @PathVariable(value = "contentIdx") final int contentIdx) {
        try {
            final int userIdx = JwtUtils.decode(httpServletRequest.getHeader(AUTHORIZATION)).getUser_idx();
            DefaultRes<Content> defaultRes = contentService.findByContentIdx(contentIdx);
            if (userIdx == defaultRes.getData().getU_id()) defaultRes.getData().setAuth(true);
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 작성
     *
     * @param jwt        토큰
     * @param contentReq 글 내용
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("")
    public ResponseEntity writeContents(@RequestHeader(AUTHORIZATION) final String jwt,
                                        final ContentReq contentReq) {
        try {
            contentReq.setU_id(JwtUtils.decode(jwt).getUser_idx());
            return new ResponseEntity<>(contentService.save(contentReq), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 좋아요
     *
     * @param jwt        토큰
     * @param contentIdx 글 고유 번호
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("/{contentIdx}/likes")
    public ResponseEntity contentLikes(@RequestHeader(AUTHORIZATION) final String jwt,
                                       @PathVariable(value = "contentIdx") final int contentIdx) {
        try {
            final int userIdx = JwtUtils.decode(jwt).getUser_idx();
            if (contentService.checkAuth(userIdx, contentIdx))
                return new ResponseEntity<>(contentService.likes(userIdx, contentIdx), HttpStatus.OK);
            else
                return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 수정
     *
     * @param jwt        토큰
     * @param contentIdx 글 번호
     * @param contentReq 글 내용
     * @param photo      사진
     * @return ResponseEntity
     */
    @Auth
    @PutMapping("/{contentIdx}")
    public ResponseEntity updateContents(
            @RequestHeader(AUTHORIZATION) final String jwt,
            @PathVariable(value = "contentIdx") final int contentIdx,
            final ContentReq contentReq,
            @RequestPart(value = "photo", required = false) final MultipartFile photo) {
        try {
            if (photo != null) contentReq.setPhoto(photo);
            final int userIdx = JwtUtils.decode(jwt).getUser_idx();
            if (contentService.checkAuth(userIdx, contentIdx)) {
                contentReq.setU_id(userIdx);
                return new ResponseEntity<>(contentService.update(contentIdx, contentReq), HttpStatus.OK);
            } else
                return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 삭제
     *
     * @param jwt        토큰
     * @param contentIdx 글 고유 번호
     * @return ResponseEntity
     */
    @Auth
    @DeleteMapping("/{contentIdx}")
    public ResponseEntity deleteContents(@RequestHeader(AUTHORIZATION) final String jwt,
                                         @PathVariable final int contentIdx) {
        try {
            final int userIdx = JwtUtils.decode(jwt).getUser_idx();
            if (contentService.checkAuth(userIdx, contentIdx))
                return new ResponseEntity<>(contentService.deleteByContentIdx(contentIdx), HttpStatus.OK);
            else
                return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
