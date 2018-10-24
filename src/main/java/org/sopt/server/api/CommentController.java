package org.sopt.server.api;

import org.sopt.server.dto.Comment;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.sopt.server.utils.auth.Auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ds on 2018-10-23.
 */

@RestController
public class CommentController {

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);

    /**
     * 해당 글의 모든 댓글 조회
     *
     * @param contentIdx
     * @return
     */
    @GetMapping("/contents/{contentIdx}/comments")
    public ResponseEntity getAllComments(@PathVariable final int contentIdx) {
        try {
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 상세 조회
     *
     * @param commentIdx
     * @return
     */
    @GetMapping("/comments/{commentIdx}")
    public ResponseEntity getComments(@PathVariable final int commentIdx) {
        try {
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 작성
     *
     * @param contentIdx
     * @return
     */
    @Auth
    @PostMapping("/contents/{contentIdx}/comments")
    public ResponseEntity writeComments(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int contentIdx,
            @RequestBody final Comment comment) {
        try {
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 좋아요
     *
     * @param commentIdx
     * @return
     */
    @Auth
    @PostMapping("/comments/{commentIdx}/likes")
    public ResponseEntity commentLikes(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int commentIdx) {
        try {
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 수정
     *
     * @param commentIdx
     * @return
     */
    @Auth
    @PutMapping("/comments/{commentIdx}")
    public ResponseEntity updateComment(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int commentIdx) {
        try {
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 삭제
     *
     * @param commentIdx
     * @return
     */
    @Auth
    @DeleteMapping("/comments/{commentIdx}")
    public ResponseEntity deleteComment(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int commentIdx) {
        try {
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
