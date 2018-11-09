package org.sopt.server.api;

import lombok.extern.slf4j.Slf4j;
import org.sopt.server.dto.Comment;
import org.sopt.server.model.DefaultRes;
import org.sopt.server.service.CommentService;
import org.sopt.server.utils.ResponseMessage;
import org.sopt.server.utils.StatusCode;
import org.sopt.server.utils.auth.Auth;
import org.sopt.server.utils.auth.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ds on 2018-10-23.
 */

@Slf4j
@RestController
public class CommentController {

    private static final String HEADER = "Authorization";

    private static final DefaultRes FAIL_DEFAULT_RES = new DefaultRes(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR);
    private static final DefaultRes UNAUTHORIZED_RES = new DefaultRes(StatusCode.UNAUTHORIZED, ResponseMessage.UNAUTHORIZED);

    private final CommentService commentService;

    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 해당 글의 모든 댓글 조회
     *
     * @param httpServletRequest Request
     * @param contentIdx         글 고유 번호
     * @return ResponseEntity
     */
    @GetMapping("/contents/{contentIdx}/comments")
    public ResponseEntity getAllComments(final HttpServletRequest httpServletRequest, @PathVariable final int contentIdx) {
        try {
            final int userIdx = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_idx();
            DefaultRes<List<Comment>> defaultRes = commentService.findByContentIdx(contentIdx);
            for (Comment c : defaultRes.getData()) {
                c.setAuth(c.getU_id() == userIdx);
                c.setLike(commentService.checkLike(userIdx, c.getC_id()));
            }
            return new ResponseEntity<>(commentService.findByContentIdx(contentIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 상세 조회
     *
     * @param httpServletRequest Request
     * @param commentIdx         댓글 고유 번호
     * @return ResponseEntity
     */
    @GetMapping("/comments/{commentIdx}")
    public ResponseEntity getComments(
            final HttpServletRequest httpServletRequest,
            @PathVariable final int commentIdx) {
        try {
            final int userIdx = JwtUtils.decode(httpServletRequest.getHeader(HEADER)).getUser_idx();
            DefaultRes<Comment> defaultRes = commentService.findByCommentIdx(commentIdx);
            defaultRes.getData().setAuth(userIdx == defaultRes.getData().getU_id());
            return new ResponseEntity<>(defaultRes, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 작성
     *
     * @param jwt        토큰
     * @param contentIdx 댓글 고유 번호
     * @param comment    댓글
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("/contents/{contentIdx}/comments")
    public ResponseEntity writeComments(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int contentIdx,
            @RequestBody Comment comment) {
        try {
            comment.setU_id(JwtUtils.decode(jwt).getUser_idx());
            comment.setB_id(contentIdx);
            return new ResponseEntity<>(commentService.save(comment), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 좋아요
     *
     * @param jwt        토큰
     * @param commentIdx 댓글 고유 번호
     * @return ResponseEntity
     */
    @Auth
    @PostMapping("/comments/{commentIdx}/likes")
    public ResponseEntity commentLikes(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int commentIdx) {
        try {
            return new ResponseEntity<>(commentService.likes(JwtUtils.decode(jwt).getUser_idx(), commentIdx), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 수정
     *
     * @param jwt        토큰
     * @param commentIdx 댓글 고유 번호
     * @param comment    댓글
     * @return ResponseEntity
     */
    @Auth
    @PutMapping("/comments/{commentIdx}")
    public ResponseEntity updateComment(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int commentIdx,
            @RequestBody final Comment comment) {
        try {
            final int userIdx = JwtUtils.decode(jwt).getUser_idx();
            comment.setC_id(commentIdx);
            if (commentService.checkAuth(userIdx, commentIdx))
                return new ResponseEntity<>(commentService.update(comment), HttpStatus.OK);
            return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 댓글 삭제
     *
     * @param commentIdx 댓글 고유 번호
     * @return ResponseEntity
     */
    @Auth
    @DeleteMapping("/comments/{commentIdx}")
    public ResponseEntity deleteComment(
            @RequestHeader("Authorization") final String jwt,
            @PathVariable final int commentIdx) {
        try {
            final int userIdx = JwtUtils.decode(jwt).getUser_idx();
            if (commentService.checkAuth(userIdx, commentIdx))
                return new ResponseEntity<>(commentService.deleteByCommentIdx(commentIdx), HttpStatus.OK);
            return new ResponseEntity<>(UNAUTHORIZED_RES, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
