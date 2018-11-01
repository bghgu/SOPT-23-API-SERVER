# Users

## 회원 조회

| 메소드 | 경로             | 짧은 설명      |
| ------ | ---------------- | -------------- |
| GET    | /users/{userIdx} | 회원 정보 조회 |

### 요청 헤더

```json
Content-Type: application/json
Authorization: token
```

### 응답 바디

#### 회원 조회(자기 자신이 조회 했을 경우)

```json
{
    "status": 200,
    "message": "회원 정보 조회 성공",
    "data": {
        "u_id": 1,
        "u_name": "배다슬",
        "u_part": "서버",
        "u_profile": "https://s3.ap-northeast-2.amazonaws.com/sopt-23-api-test/Profile-icon-9.png",
        "u_email": "1",
        "auth": true
    }
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
## 회원 가입

| 메소드 | 경로   | 짧은 설명 |
| ------ | ------ | --------- |
| POST   | /users | 회원 가입 |

### 요청 헤더

```json
Content-Type: application/json
```

### 요청 바디

```json
{
	"name" : "테스트",
	"email" : "2",
	"password" : "1234",
	"part" : "서버"
}
```

### 응답 바디

#### 회원 가입 성공

```json
{
    "status": 201,
    "message": "회원 가입 성공",
    "data": null
}
```
#### 회원 가입 실패

```json
{
    "status": 400,
    "message": "회원 가입 실패",
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

## 회원 정보 수정

| 메소드 | 경로             | 짧은 설명      |
| ------ | ---------------- | -------------- |
| PUT    | /users/{userIdx} | 회원 정보 수정 |

### 요청 헤더

```json
Content-Type: application/json
Application: token
```

### 요청 바디

```json
{
	"name" : "테스트",
	"email" : "2",
	"password" : "1234",
	"part" : "서버"
}
```

### 응답 바디

#### 회원 정보 수정 성공

```json
{
    "status": 200,
    "message": "회원 정보 수정 성공",
    "data": {
        "u_id": 1,
        "u_name": "배다슬",
        "u_part": "서버",
        "u_profile": "https://s3.ap-northeast-2.amazonaws.com/sopt-23-api-test/Profile-icon-9.png",
        "u_email": "1",
        "auth": true
    }
}
```

#### 회원 정보 수정 실패

```JSon
{
    "status": 400,
    "message": "회원 정보 수정 실패",
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

#### 다른 회원 정보 수정 시도

```json
{
    "status": 403,
    "message": "인가 실패",
    "data": false
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

## 회원 탈퇴

| 메소드 | 경로             | 짧은 설명 |
| ------ | ---------------- | --------- |
| DELETE | /users/{userIdx} | 회원 탈퇴 |

### 요청 헤더

```json
Content-Type: application/json
Application: token
```

### 응답 바디

#### 회원 탈퇴 성공

```json
{
    "status": 204,
    "message": "회원 탈퇴 성공",
    "data": null
}
```

#### 다른 회원 탈퇴 시도

```json
{
    "status": 403,
    "message": "인가 실패",
    "data": false
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