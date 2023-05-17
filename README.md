# 게시판 프로젝트😊 [Spring]
<br><br>

## 프로젝트 주제
* * *
- spring & spring-boot framework 활용한 게시판 웹 어플리케이션
<br><br>

## 주요 기능
* * *
- 관리자
	- 게시판 설정 등록 및 관리
	- 회원 관리
	- 게시글 관리
	- 첨부 파일 관리
- 사용자
	- 게시글 작성
	- 게시글 수정
	- 게시글 삭제
	- 게시글 검색
	- 회원 가입
	- 로그인
	- 회원 정보 수정
	- 회원 탈퇴
<br><br>

## 개발 일정
* * *
- 2023.05.15 ~
<br><br>

## Technical Spec
* * *
- Language : Java(JDK 20)
- Framework : Spring boot, Spring
- Build Tool : Maven
- DataBase : MySql
<br><br>

## 기능 상세 설명
* * *
### 회원가입
- spring data JPA로 회원 엔티티 및 레포지토리 코드 구현.
  * MemberEntity 생성
    - 회원명, 이메일 관련 인덱스 생성
    - email 컬럼 : 회원 정보 조회 시 본인 인증 수단 구현 예정
  * BaseEntity 생성
    - 추상 클래스로 생성(공통 기능 역할)
    - 하위 엔티티에 등록/수정일자 자동 추가하여 관리하기 위한 엔티티
  * MemberRepository 인터페이스 생성
    - findByMemberId : 회원 아이디로 회원 조회 기능
    - isExists() 디폴트 메서드 : 회원 아이디 중복 조회 시 사용, Querydsl 사용하여 구현
- 템플릿 join.html 및 커맨드 객체 JoinForm 구현.
  * join.html 생성 및 타임리프로 요청 데이터 폼 작성
  * 커맨드 객체 JoinForm 생성 : joinPS() 요청 데이터 매핑용 객체
- Bean Validation, 추가 검증 JoinValidator 구현.
  * Bean Validation : 필수 입력값 검증, 입력값 크기 검증
  * JoinValidator 
    - 아이디 중복 확인
    - 비밀번호 복잡성 확인(영대소문자, 특수문자, 숫자 각각 1자이상)
    - 비밀번호 - 비밀번호 확인 일치 여부 확인
    - 휴대전화번호 양식 및 DB 저장시 숫자만 입력되도록 구현
    - 회원가입 필수 약관 동의 확인
- MemberSaveService 구현.
  * ModelMapper로 JoinForm 입력데이터를 MemberEntity와 매핑
  * 회원 권한 사용자로 초기화
  * 비밀번호 해쉬화
    - SecurityConfig : passwordEncoder(인터페이스) 빈 등록
    - MemberSaveService에서 비밀번호 해쉬화 코드 구현
- MemberController 구현. 
  * GET 방식 : String join(`Model model`) - url : /member/join, return : member/join
  * POST 방식 : String joinPs(`@Valid JoinForm joinForm`,` Errors errors`) - url : /member/join, return : redirect:/member/login
<br><br>

### 로그인(스프링 시큐리티)
- SecurityConfig & Role 이넘클래스 구현.
  * SecurityFilterChain filterChain(HttpSecurity http) extends Exception
    - 로그인, 로그인 페이지 설정, 로그아웃 페이지 설정, 로그아웃, 로그아웃 url, 로그아웃 성공 시 이동 페이지 설정.
  * Role 이넘클래스 상수로 권한 지정(USER, ADMIN)
  * LoginSuccessHandler & LoginFailureHandler & LoginValidationException
    - 로그인 성공 또는 실패 시 세션 처리 및 이동 페이지 설정.
    - LoginValidationException 예외 클래스 - CommonException 예외 상속
  * exceptionHandling() 구현
    - 인증, 인가 후처리
- login.html 템플릿 구현.
- UserDetails,UserDetailsService 구현.
- AwareAuditor 인터페이스 구현.
- 로그인한 회원 정보 조회 기능 구현.
- 로그인 시 권한 별 header 노출 항목 관련, thymeleaf-security로 구현.
### 게시판
### 게시글
### 검색
### 기타
- Interceptor : 두개 이상의 controller 에서 공통으로 사용할 기능을 정의하기 위함.
  * HandlerInterceptor 인터페이스 구현.
    - preHandle() : 컨트롤러 실행 전
    - postHandle() : 컨트롤러 실행 후, 뷰 실행 전
    - afterCompletion() : 뷰 실행 후
  * SiteConfigInterceptor 클래스
    - preHandle()
        * css, js 버전 설정.
<br><br>

## ERD
* * *

## Trouble-Shooting
* * *
- 2023.05.17
  - PasswordValidator : 특수문자 입력하지 않았는데, 오류 메시지 발생하지 않음.
  - SecurityConfig : 로그인 후에는 인증, 인가 후처리가 적용 안되는것 같음, 별도로 기능을 넣어야할 것 같다.