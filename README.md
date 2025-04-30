🌍 프로젝트 개요
목표: Jira/Notion처럼 팀 협업과 프로젝트 관리에 필요한 기능을 제공하는 플랫폼 구축

- 기술 스택: Backend: Spring Boot, JWT, Spring Security, MariaDB
- Frontend: React, Axios
- 개발 도구: IntelliJ (백엔드), VSCode (프론트엔드), GitHub 연동

✅ 진행된 작업 정리
1. 백엔드 프로젝트 세팅
루트 패키지: com.irnproj.easycollab
Gradle 기반 Spring Boot 프로젝트 생성 완료
MariaDB 연동 및 application.propeties 파일 설정

2. 회원가입 & 로그인 (JWT 기반)
엔티티, DTO, Repository, Service, Controller 구조로 모듈화
비밀번호 암호화(BCryptPasswordEncoder) 적용
JWT 유틸 (JwtUtil) 구현
JWT 인증 필터 (JwtAuthenticationFilter) 구현
Spring Security 설정 (SecurityConfig)
CORS 설정
JWT 필터 체인 등록
인증되지 않은 요청 처리 방식 지정
🔄 최근에는 SecurityConfig - UserService - UserDetailsServiceImpl 간 순환 참조 문제 해결 중

3. 회원가입/로그인 테스트 준비
Postman을 활용한 API 테스트 예정
DB 초기화 및 테이블 재생성 설정

4. 팀 기능 구현 시작
팀 관련 Entity/DTO/Service/Controller 구조 설계
로그인 후 이동할 /teams 페이지 계획 수립


📌 다음 단계 진행 예정

- 순환 참조 문제 해결 후 로그인 API 정상 작동 확인
- 회원가입 → 로그인 → JWT 토큰 발급 → 인증 테스트(Postman 등)
- 팀 생성 및 팀 목록 API 연동
- 프론트엔드 React 로그인/회원가입 폼 개발 및 API 연동
- 로그인 후 상태 관리 및 페이지 이동 (ex. /teams)
-  팀 목록 조회 + 생성 UI 개발
