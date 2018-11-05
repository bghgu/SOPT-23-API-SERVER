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

    private static final String HEADER = "Authorization";

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final ContentService contentService;

    public ContentController(final ContentService contentService) {
        this.contentService = contentService;
    }


    @GetMapping("")
    public ResponseEntity getAllContents(final Pagination pagination) {
        try {
            return new ResponseEntity<>(contentService.findAll(pagination), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{contentIdx}")
    public ResponseEntity getContents(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int contentIdx) {
        try {
            final String jwt = httpServletRequest.getHeader(HEADER);
            if(jwt != null) return new ResponseEntity<>(contentService.findByContentIdx(JwtUtils.decode(jwt).get().getUser_idx(), contentIdx), HttpStatus.OK);
            return new ResponseEntity<>(contentService.findByContentIdx(0, contentIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 작성
     * @param contentReq
     * @return
     */
    @Auth
    @PostMapping("")
    public ResponseEntity writeContents(
            @RequestHeader("Authorization") final String jwt,
            final ContentReq contentReq) {
        try {
            contentReq.setU_id(JwtUtils.decode(jwt).get().getUser_idx());
            log.info(contentReq.toString());
            return new ResponseEntity<>(contentService.save(contentReq), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 좋아요
     * @param contentIdx
     * @return
     */
    @Auth
    @PostMapping("/{contentIdx}/likes")
    public ResponseEntity contentLikes(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int contentIdx) {
        try {
            final int temp = JwtUtils.decode(jwt).get().getUser_idx();
            return new ResponseEntity<>(contentService.likes(temp, contentIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 수정
     * @param contentIdx
     * @return
     */
    @Auth
    @PutMapping("/{contentIdx}")
    public ResponseEntity updateContents(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int contentIdx,
            final ContentReq contentReq,
            @RequestPart(value = "photo", required = false) final MultipartFile photo) {
        try {
            if(photo != null) contentReq.setPhoto(photo);
            contentReq.setU_id(JwtUtils.decode(jwt).get().getUser_idx());
            return new ResponseEntity<>(contentService.update(contentIdx, contentReq), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 삭제
     * @param contentIdx
     * @return
     */
    @Auth
    @DeleteMapping("/{contentIdx}")
    public ResponseEntity deleteContents(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int contentIdx) {
        try {
            return new ResponseEntity<>(contentService.deleteByContentIdx(JwtUtils.decode(jwt).get().getUser_idx(), contentIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
