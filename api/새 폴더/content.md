# Contents

## 모든 글 조회

| 메소드 | 경로                                                      | 짧은 설명      |
| ------ | --------------------------------------------------------- | -------------- |
| GET    | /contents?offset={offset}&limit={limit}&keyword={keyword} | 회원 정보 조회 |

### QueryString 설명

| Parameter | 설명                            | 예시           | 값 범위     |
| --------- | ------------------------------- | -------------- | ----------- |
| offset    | 시작 번호(기본값 = 0)           | offset=0       | 0 이상 정수 |
| limit     | 가져올 데이터 갯수(기본값 = 10) | limit=10       | 1 이상 정수 |
| keyword   | 검색어(기본값 = "")             | keyword=검색어 | String      |

### 요청 헤더

```json
Content-Type: application/json
```

### 응답 바디

#### 회원 조회(자기 자신이 조회 했을 경우)

```json
{
    "status": 200,
    "message": "모든 글 조회 성공",
    "data": [
        {
            "b_id": 11,
            "b_title": "글 제목2",
            "b_contents": "내용내용내용",
            "b_date": "2018-11-03T13:47:35.000+0000",
            "u_id": 2,
            "b_like": 0,
            "photo": null,
            "b_photo": null,
            "auth": false,
            "like": false
        },
        {
            "b_id": 20,
            "b_title": "10",
            "b_contents": "",
            "b_date": "2018-11-03T13:47:35.000+0000",
            "u_id": 2,
            "b_like": 0,
            "photo": null,
            "b_photo": null,
            "auth": false,
            "like": false
        }
    ]
}
```
#### 회원 조회(타인이 조회 했을 경우)

```json
{
    "status": 200,
    "message": "회원 정보 조회 성공",
    "data": {
        "u_id": 2,
        "u_name": "테스트",
        "u_part": "서버",
        "u_profile": null,
        "u_email": "2",
        "auth": false
    }
}
```
#### 회원 조회 실패

```json
{
    "status": 404,
    "message": "회원을 찾을 수 없습니다.",
    "data": null
}
```
#### INTERNAL SERVER ERROR

```json
{
    "status": 500,
    "message": "서버 내부 에러",
    "data": null
}
```
------
## 글 조회

| 메소드 | 경로                   | 짧은 설명   |
| ------ | ---------------------- | ----------- |
| GET    | /contents/{contentIdx} | 글 조회 |

### 요청 헤더

```json
Content-Type: application/json
Authorization: token
```

### 응답 바디

#### 글 조회 성공

```json
{
    "status": 200,
    "message": "글 조회 성공",
    "data": {
        "b_id": 11,
        "b_title": "1",
        "b_contents": "",
        "b_date": "2018-11-03T13:47:35.000+0000",
        "u_id": 2,
        "b_like": 0,
        "b_photo": null,
        "auth": false,
        "like": false
    }
}
```
#### 없는 글 조회

```json
{
    "status": 404,
    "message": "글이 존재하지 않습니다.",
    "data": null
}
```
#### INTERNAL SERVER ERROR

```json
{
    "status": 500,
    "message": "서버 내부 에러",
    "data": null
}
```
------

## 글 작성

| 메소드 | 경로      | 짧은 설명   |
| ------ | --------- | ----------- |
| POST   | /contents | 글 작성 |

### 요청 헤더

```json
Content-Type: multipart/form-data
Application: token
```

### 요청 바디

```json
{
	"title" : "제목",
	"contents" : "내용",
	"photo" : "파일"
}
```

### 응답 바디

#### 글 작성 성공

```json
{
    "status": 201,
    "message": "글 작성 성공",
    "data": null
}
```

#### 글 작성 실패

```JSon
{
    "status": 400,
    "message": "글 작성 실패",
    "data": null
}
```

#### 인증 실패

```json
{
    "status": 401,
    "message": "인증 실패",
    "data": null
}
```

#### DB 에러

```json
{
    "status": 600,
    "message": "데이터베이스 에러",
    "data": null
}
```

#### INTERNAL SERVER ERROR

```json
{
    "status": 500,
    "message": "서버 내부 에러",
    "data": null
}
```
------

## 글 좋아요/취소

| 메소드 | 경로                         | 짧은 설명          |
| ------ | ---------------------------- | ------------------ |
| POST   | /contents/{contentIdx}/likes | 글 좋아요/취소 |

### 요청 헤더

```json
Content-Type: application/json
Application: token
```

### 응답 바디

#### 글 좋아요 성공

```json
{
    "status": 200,
    "message": "글 좋아요",
    "data": {
        "b_id": 22,
        "b_title": "제목테스트",
        "b_contents": "내용 테스트",
        "b_date": "2018-11-04T01:58:34.000+0000",
        "u_id": 2,
        "b_like": 1,
        "photo": null,
        "b_photo": "https://s3.ap-northeast-2.amazonaws.com/sopt-23-api-test/08c059049d98477b912ed0d45c3e246c.JPG",
        "auth": true,
        "like": true
    }
}
```

#### 글 좋아요 취소

```JSon
{
    "status": 200,
    "message": "글 좋아요 해제",
    "data": {
        "b_id": 22,
        "b_title": "제목테스트",
        "b_contents": "내용 테스트",
        "b_date": "2018-11-04T01:58:34.000+0000",
        "u_id": 2,
        "b_like": 0,
        "photo": null,
        "b_photo": "https://s3.ap-northeast-2.amazonaws.com/sopt-23-api-test/08c059049d98477b912ed0d45c3e246c.JPG",
        "auth": true,
        "like": false
    }
}
```

#### 없는 글 좋아요

```json
{
    "status": 401,
    "message": "인증 실패",
    "data": null
}
```

#### 인증 실패

```json
{
    "status": 401,
    "message": "인증 실패",
    "data": null
}
```

#### DB 에러

```json
{
    "status": 600,
    "message": "데이터베이스 에러",
    "data": null
}
```

#### INTERNAL SERVER ERROR

```json
{
    "status": 500,
    "message": "서버 내부 에러",
    "data": null
}
```

------

## 글 수정

| 메소드 | 경로                   | 짧은 설명   |
| ------ | ---------------------- | ----------- |
| PUT    | /contents/{contentIdx} | 글 수정 |

### 요청 헤더

```json
Content-Type: multipart/form-data
Application: token
```

### 요청 바디

```json
{
	"title" : "제목",
	"contents" : "내용",
	"photo" : "파일"
}
```

### 응답 바디

#### 글 수정 성공

```json
{
    "status": 200,
    "message": "글 수정 성공",
    "data": {
        "b_id": 22,
        "b_title": "123123123",
        "b_contents": "내용 테스트",
        "b_date": "2018-11-04T02:52:03.000+0000",
        "u_id": 2,
        "b_like": 1,
        "photo": null,
        "b_photo": "https://s3.ap-northeast-2.amazonaws.com/sopt-23-api-test/2b51f4250a4f48b4b49bcf2318b1bdb7.png",
        "auth": true,
        "like": true
    }
}
```

#### 다른 회원 글 수정 시도

```json
{
    "status": 403,
    "message": "인가 실패",
    "data": false
}
```

#### 없는 글 수정 시도

```json
{
    "status": 404,
    "message": "글이 존재하지 않습니다.",
    "data": null
}
```

#### 인증 실패

```json
{
    "status": 401,
    "message": "인증 실패",
    "data": null
}
```

#### DB 에러

```json
{
    "status": 600,
    "message": "데이터베이스 에러",
    "data": null
}
```

#### INTERNAL SERVER ERROR

```json
{
    "status": 500,
    "message": "서버 내부 에러",
    "data": null
}
```
------
## 글 삭제

| 메소드 | 경로                   | 짧은 설명   |
| ------ | ---------------------- | ----------- |
| DELETE | /contents/{contentIdx} | 글 삭제 |

### 요청 헤더

```json
Content-Type: application/json
Application: token
```

### 응답 바디

#### 글 삭제 성공

```json
{
    "status": 204,
    "message": "회원 탈퇴 성공",
    "data": null
}
```

#### 다른 회원의 글 삭제 시도

```json
{
    "status": 403,
    "message": "인가 실패",
    "data": false
}
```

#### 글 삭제 실패

```json
{
    "status": 404,
    "message": "회원을 찾을 수 없습니다.",
    "data": null
}
```

#### 인증 실패

```json
{
    "status": 401,
    "message": "인증 실패",
    "data": null
}
```

#### DB 에러

```json
{
    "status": 600,
    "message": "데이터베이스 에러",
    "data": null
}
```

#### INTERNAL SERVER ERROR

```json
{
    "status": 500,
    "message": "서버 내부 에러",
    "data": null
}
```
------