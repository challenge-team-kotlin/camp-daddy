# CAMP DADDY

- 프로젝트 명 : 캠프 대디
- 프로젝트 소개 : 유저 간 캠핑용품 대여 플랫폼
- 프로젝트 계기 : 여러 캠핑용품을 경험해보고 싶은 유저와, 사용 빈도가 낮은 캠핑용품을 대여하여 수익을 얻고자 하는 유저 간의 수요를 만족하는 서비스

## 개발 인원

| 백승한                                                       | 최준홍                                                        | 이승상                                                        | 윤승환                                                        |
|-----------------------------------------------------------|------------------------------------------------------------|------------------------------------------------------------|------------------------------------------------------------|
| ![](https://avatars.githubusercontent.com/u/84169773?v=4) | ![](https://avatars.githubusercontent.com/u/100489972?v=4) | ![](https://avatars.githubusercontent.com/u/150119073?v=4) | ![](https://avatars.githubusercontent.com/u/43734328?v=4) |
| [@megaseunghan](https://github.com/megaseunghan)          | [@zunbeeok](https://github.com/zunbeeok)                   | [@soang94](https://github.com/soang94)                     | [@lovelyunsh](https://github.com/lovelyunsh)                   |
| `팀장`, `채팅`, `CI/CD`                                       | `상품`                                                       | `소셜 로그인`, `멤버`                                             | `리뷰`, `예약`                                       |

## 사용 기술
![image](https://github.com/megaseunghan/demo/assets/84169773/d68c88e2-3ef3-43d0-9ed5-adb8efae3018)

## ERD
![image](https://github.com/megaseunghan/demo/assets/84169773/087f99ab-07b0-468c-932a-6525c279d641)

## CI/CD 파이프라인
![](https://github.com/megaseunghan/demo/assets/84169773/8e19d2d0-ee31-4d33-93e0-788ee9d3be6c)
## 프로젝트 패키지 구조

```markdown
├── main
│   ├── kotlin
│   │   └── com
│   │       └── challengeteamkotlin
│   │           └── campdaddy
│   │               ├── application
│   │               │   ├── auth
│   │               │   │   └── exception
│   │               │   ├── chat
│   │               │   │   └── exception
│   │               │   ├── member
│   │               │   │   └── exception
│   │               │   ├── product
│   │               │   │   └── exception
│   │               │   ├── reservation
│   │               │   │   ├── exception
│   │               │   │   └── handler
│   │               │   └── review
│   │               │       └── execption
│   │               ├── common
│   │               │   ├── config
│   │               │   │   └── chat
│   │               │   │       └── handler
│   │               │   ├── entity
│   │               │   ├── exception
│   │               │   │   ├── advice
│   │               │   │   ├── code
│   │               │   │   └── dto
│   │               │   │       └── response
│   │               │   └── security
│   │               ├── domain
│   │               │   ├── model
│   │               │   │   ├── chat
│   │               │   │   ├── member
│   │               │   │   ├── product
│   │               │   │   ├── reservation
│   │               │   │   └── review
│   │               │   └── repository
│   │               │       ├── chat
│   │               │       ├── member
│   │               │       ├── product
│   │               │       │   └── dto
│   │               │       ├── reservation
│   │               │       └── review
│   │               ├── infrastructure
│   │               │   ├── client
│   │               │   │   ├── config
│   │               │   │   ├── kakao
│   │               │   │   └── properties
│   │               │   ├── hibernate
│   │               │   │   ├── chat
│   │               │   │   ├── member
│   │               │   │   ├── product
│   │               │   │   ├── reservation
│   │               │   │   └── review
│   │               │   └── jwt
│   │               └── presentation
│   │                   ├── auth
│   │                   │   └── dto
│   │                   │       └── response
│   │                   ├── chat
│   │                   │   └── dto
│   │                   │       ├── request
│   │                   │       └── response
│   │                   ├── member
│   │                   │   └── dto
│   │                   │       ├── request
│   │                   │       └── response
│   │                   ├── product
│   │                   │   └── dto
│   │                   │       ├── request
│   │                   │       └── response
│   │                   ├── reservation
│   │                   │   └── dto
│   │                   │       ├── reqeust
│   │                   │       └── response
│   │                   └── review
│   │                       └── dto
│   │                           ├── request
│   │                           └── response
│   └── resources
└── test
├── kotlin
│   └── com
│       └── challengeteamkotlin
│           └── campdaddy
│               ├── application
│               │   ├── auth
│               │   ├── chat
│               │   ├── member
│               │   ├── reservation
│               │   └── review
│               ├── domain
│               │   ├── model
│               │   │   ├── chat
│               │   │   ├── member
│               │   │   ├── product
│               │   │   ├── reservation
│               │   │   └── review
│               │   └── repository
│               │       └── chat
│               ├── fixture
│               │   ├── auth
│               │   ├── chat
│               │   ├── member
│               │   ├── product
│               │   ├── region
│               │   ├── reservation
│               │   ├── review
│               │   ├── sido
│               │   └── sigg
│               └── infrastructure
│                   └── client
│                       └── kakao
└── resources

```


