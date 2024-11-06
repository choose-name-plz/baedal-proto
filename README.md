# 🐿️ 2조 - 2제 팀이름을 정해조

> 배달 중계 서비스

---

## 목차

- [🐒 구성원](#-구성원)
- [🐹 개발 환경](#-개발-환경)
- [👻 상세 개발 환경](#-상세-개발-환경)
- [🐰 프로젝트 상세](#-프로젝트-상세)
- [🐳 ERD](#-erd)
- [🐙 API docs](#-api-docs)
- [🐬 인프라 구조](#-인프라-구조)
- [🐠 실행 방법](#-실행-방법)
- [🐊 발표 PPT 자료](#-발표-ppt-자료)

## 🐒 구성원

| 이름                                         | 역할 분담                        |
|--------------------------------------------|------------------------------|
| [김재윤](https://github.com/yunjae62)        | 주문, 주문상품, 결제, 리뷰, 배포         |
| [김형철](https://github.com/shurona)          | 가게, 상품, 카테고리, 지리, 공지사항, 고객센터 |
| [조성진](https://github.com/korean-jindo-dog) | 유저, 인증/인가, AI                |

## 🐹 개발 환경

| 분류         | 상세                                  |
|------------|:------------------------------------|
| IDE        | IntelliJ                            |
| Language   | Java 17                             |
| Framework  | Spring Boot 3.3.3                   |
| Repository | H2 In-memory(Test), PostgreSQL 16.4 |
| Build Tool | Gradle 8.8                          |
| Infra      | EC2, Docker, Github Actions         |

## 👻 상세 개발 환경

### Dependencies

- Spring WebMVC
- Spring Validation
- Spring Security
- Spring Data Jpa
- Spring Data Redis
- Thymeleaf
- jjwt 0.12.5
- QueryDSL 5.0.0
- mapStruct 1.5.5.Final
- Lombok
- JUnit
- Swagger 2.6.0
- Jacoco

## 🐰 프로젝트 상세

### 도메인 주도 설계 (DDD) 일부 도입

![DDD-v3](./docs/aggregate-v3.png)

총 10개의 애그리거트로 구성되어 있습니다.

## 🐳 ERD

![ERD](./docs/erd-v3.png)

- [ErdCloud](https://www.erdcloud.com/d/wKTSxmnmGstaHiwgS)

## 🐙 API docs

- [Swagger UI](https://app.swaggerhub.com/apis-docs/choose-name-plz/baedal-proto/0.0.1)

## 🐬 인프라 구조

![Infra](./docs/infra.png)

## 🐠 실행 방법

### 환경 변수 목록

> AI_API_KEY={Gemini API Key}

- 이것만 입력하면 Docker compose support 라이브러리가 있어서 스프링만 실행하면 됩니다.

## 🐊 발표 PPT 자료

- [Canva](https://www.canva.com/design/DAGPluXO22A/1D-FAsErLIMDBwZ5kdgrvg/view?utm_content=DAGPluXO22A&utm_campaign=designshare&utm_medium=link&utm_source=editor)

