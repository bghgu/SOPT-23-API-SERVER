package org.sopt.server.api;

import org.sopt.server.dto.Content;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.service.ContentService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.sopt.server.utils.auth.Auth;
import org.sopt.server.utils.auth.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ds on 2018-10-23.
 */

@RestController
@RequestMapping("/contents")
public class ContentController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    private final ContentService contentService;

    public ContentController(final ContentService contentService) {
        this.contentService = contentService;
    }

    /**
     * 모든 게시글 조회
     * @param offset
     * @param limit
     * @param sort
     * @param order
     * @param keyword
     * @return
     */
    @GetMapping("")
    public ResponseEntity getAllContents(
            @RequestParam(value = "offset", defaultValue = "0", required = false) final int offset,
            @RequestParam(value = "limit", defaultValue = "10", required = false) final int limit,
            @RequestParam(value = "sort", defaultValue = "productIdx", required = false) final String sort,
            @RequestParam(value = "order", defaultValue = "desc", required = false) final String order,
            @RequestParam(value = "keyword", defaultValue = "", required = false) final String keyword
    ) {
        try {
            return new ResponseEntity<>(contentService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 상세 조회
     * @param contentIdx
     * @return
     */
    @GetMapping("/{contentIdx}")
    public ResponseEntity getContents(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int contentIdx) {
        try {
            final int temp = JwtUtils.decode(jwt).get().getUser_idx();
            return new ResponseEntity<>(contentService.findByContentIdx(temp, contentIdx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 글 작성
     * @param content
     * @return
     */
    @Auth
    @PostMapping("")
    public ResponseEntity writeContents(
            @RequestHeader("Authorization") final String jwt,
            @RequestBody Content content) {
        try {
            content.setU_id(JwtUtils.decode(jwt).get().getUser_idx());
            return new ResponseEntity<>(contentService.save(content), HttpStatus.OK);
        } catch (Exception e) {
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
            @RequestBody Content content) {
        try {
            content.setU_id(JwtUtils.decode(jwt).get().getUser_idx());
            return new ResponseEntity<>(contentService.update(contentIdx, content), HttpStatus.OK);
        } catch (Exception e) {
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
            final int temp = JwtUtils.decode(jwt).get().getUser_idx();
            return new ResponseEntity<>(contentService.deleteByContentIdx(temp, contentIdx), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
