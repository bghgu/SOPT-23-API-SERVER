# Auth

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
#### 중복 이메일

```JSon
{
    "status": 400,
    "message": "이미 존재하는 Email입니다.",
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
## 로그인

| 메소드 | 경로   | 짧은 설명 |
| ------ | ------ | --------- |
| POST   | /login | 로그인    |

### 요청 헤더

```json
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
    "status": 200,
    "message": "로그인 성공",
    "data": {
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEb0lUU09QVCIsInVzZXJfaWR4IjoxfQ.5lCvAqnzYP4-2pFx1KTgLVOxYzBQ6ygZvkx5jKCFM08"
    }
}
```
#### 로그인 실패

```json
{
    "status": 400,
    "message": "로그인 실패",
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
