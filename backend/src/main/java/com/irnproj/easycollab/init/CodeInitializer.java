package com.irnproj.easycollab.init;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("dev") // ✅ 개발 환경에서만 실행됨
@RequiredArgsConstructor
public class CodeInitializer {

  private final ComCodeRepository comCodeRepository;

  @PostConstruct
  public void init() {
    // 사용자 직급 코드
    insertCode("ROLE", "OWNER", "소유자", "팀을 생성한 사람");
    insertCode("ROLE", "ADMIN", "관리자", "팀 관리 권한 보유자");
    insertCode("ROLE", "MEMBER", "팀원", "일반 팀원");
    insertCode("ROLE", "GUEST", "게스트", "읽기 전용 멤버");

    // 이슈/프로젝트 상태 코드
    insertCode("STATUS", "PLANNED", "예정", "시작 전 상태");
    insertCode("STATUS", "IN_PROGRESS", "진행중", "작업 중인 상태");
    insertCode("STATUS", "HOLD", "보류", "일시 중지 상태");
    insertCode("STATUS", "COMPLETED", "완료", "작업 완료");
    insertCode("STATUS", "URGENT", "긴급", "우선 처리 필요");
  }

  private void insertCode(String codeType, String code, String name, String description) {
    boolean exists = comCodeRepository.existsByCodeTypeAndCode(codeType, code);
    if (!exists) {
      ComCode codeEntity = ComCode.builder()
          .codeType(codeType)
          .code(code)
          .name(name)
          .description(description)
          .build();
      comCodeRepository.save(codeEntity);
    }
  }

}