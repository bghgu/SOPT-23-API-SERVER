# URI

## Auths

| 메소드 | 경로          | 설명          |
| ------ | ------------- | ------------- |
| POST   | /auths/login  | 로그인        |
| GET    | /auths/logout | 로그아웃      |
| POST   | /auths/find   | 비밀번호 찾기 |

## Users

| 메소드 | 경로             | 설명           |
| ------ | ---------------- | -------------- |
| GET    | /users/{userIdx} | 회원 조회      |
| POST   | /users           | 회원 가입      |
| PUT    | /users/{userIdx} | 회원 정보 수정 |
| DELETE | /users/{userIdx} | 회원 탈퇴      |

## Contents

| 메소드 | 경로                                                         | 설명             |
| ------ | ------------------------------------------------------------ | ---------------- |
| GET    | /contents?offset={offset}&limit={limit}&sort={sort}&order={order}&keyword={keyword} | 모든 게시글 조회 |
| GET    | /contents/{contentIdx}                                       | 글 상세 조회     |
| POST   | /contents                                                    | 글 작성          |
| POST   | /contents/{contentIdx}/likes                                 | 글 좋아요        |
| PUT    | /contents/{contentIdx}                                       | 글 수정          |
| DELETE | /contents/{contentIdx}                                       | 글 삭제          |

## Comments

| 메소드 | 경로                            | 설명                     |
| ------ | ------------------------------- | ------------------------ |
| GET    | /contents/{contentIdx}/comments | 해당 글의 모든 댓글 조회 |
| GET    | /comments/{commentIdx}          | 댓글 상세 조회           |
| POST   | /contents/{contentIdx}/comments | 해당 글에 댓글 작성      |
| POST   | /conmments/{commentIdx}/likes   | 댓글 좋아요              |
| PUT    | /comments/{commentIdx}          | 댓글 수정                |
| DELETE | /comments/{commentIdx}          | 댓글 삭제                |

