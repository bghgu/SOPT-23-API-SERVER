# Auth

## 회원 가입

| 메소드 | 경로       | 짧은 설명 |
| ------ | ---------- | --------- |
| POST   | /api/users | 회원 가입 |

### 요청 헤더

```
Content-Type: application/json
```

### 요청 바디

```
{
    "email": "bghgu@naver.com",
    "password": "1234",
    ""
}
```

### 응답 바디

#### 회원 조회 성공

```json
{
    "statusCode": 200,
    "responseMessage": "Success Find User",
    "responseData": {
        "userIdx": 1,
        "email": "bghgu@naver.com",
        "name": "배다슬"
    }
}
```
#### 회원 조회 실패

```json
{
    "statusCode": 404,
    "responseMessage": "Not Find User",
    "responseData": null
}
```
#### INTERNAL SERVER ERROR

```json
{
    "statusCode": 500,
    "responseMessage": "INTERNAL_SERVER_ERROR",
    "responseData": null
}
```
------
## 로그인

| 메소드 | 경로       | 짧은 설명 |
| ------ | ---------- | --------- |
| POST   | /api/login | 로그인    |

### 요청 헤더

```
Content-Type: application/json
```

### 요청 바디

```json
{
    "email": "bghgu@naver.com",
    "password": "1234"
}
```

### 응답 바디

#### 로그인 성공

```json
{
    "statusCode": 200,
    "responseMessage": "Login Success",
    "responseData": {
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEb0lUU09QVCIsInVzZXJfaWR4IjoxfQ.5lCvAqnzYP4-2pFx1KTgLVOxYzBQ6ygZvkx5jKCFM08"
    }
}
```
#### 로그인 실패

```json
{
    "statusCode": 400,
    "responseMessage": "Login Fail",
    "responseData": null
}
```
#### INTERNAL SERVER ERROR

```json
{
    "statusCode": 500,
    "responseMessage": "INTERNAL_SERVER_ERROR",
    "responseData": null
}
```
------