
EasyCollab - Fullstack 프로젝트
===============================

간단한 협업 도구 프로젝트입니다. 
사용자 로그인 후 팀을 생성하고, 본인이 만든 팀을 조회할 수 있습니다.
백엔드는 Spring Boot, 프론트엔드는 React 기반으로 개발되었습니다.

🛠 기술 스택
------------
- Backend: Java, Spring Boot, Spring Security, JWT, JPA, MariaDB
- Frontend: React (TypeScript), Axios, React Router DOM
- 공통: RESTful API, JWT 기반 인증

📂 폴더 구조 (Frontend)
------------------------
src/

├── api/               # Axios 인스턴스 및 interceptor

├── components/        # LoginForm, TeamForm, TeamList

├── pages/             # LoginPage, TeamPage

├── styles/            # CSS

└── App.tsx            # 전체 라우팅 구성

📂 주요 구조 (Backend)
------------------------
com.irnproj.easycollab

├── module.user        # 사용자 엔티티, 컨트롤러, 서비스

├── module.team        # 팀 생성/조회 관련 로직

├── security           # JWT 필터, Security 설정, UserPrincipal

└── EasycollabApplication.java

🚀 시작하기
------------
1. 백엔드 실행 (8080 포트)
   - application.yml에서 DB 설정 확인
   - `./gradlew bootRun`

2. 프론트엔드 실행 (3000 포트)
   - npm install
   - npm start

3. 로그인 → 토큰 저장 → 팀 생성/조회 가능

🔐 JWT 인증 흐름
----------------
- 로그인 성공 시 서버가 JWT 발급
- 클라이언트는 localStorage에 저장 후 모든 요청에 Authorization 헤더 포함
- Spring Security의 JWT 필터가 이를 파싱해 인증 처리

🧾 API 명세
------------
| 메서드 | 경로          | 설명               | 인증 필요 |
|--------|---------------|--------------------|------------|
| POST   | /auth/login   | 로그인 요청         | ❌         |
| POST   | /teams        | 팀 생성            | ✅         |
| GET    | /teams        | 본인 팀 목록 조회   | ✅         |

✅ 향후 작업 제안
------------------
- 팀 수정 및 삭제 기능 추가
- 팀 상세 페이지, 팀원 관리 기능
- 인증 미로그인 시 접근 제한 및 리다이렉트
- 로그인 상태 전역 관리 (Context/Redux)
- 예외 메시지 및 응답 일관성 개선
