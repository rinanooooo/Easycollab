package com.irnproj.easycollab.init;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev") // dev 프로필일 때만 실행
@RequiredArgsConstructor
public class CodeInitializer {

  private final ComCodeRepository comCodeRepository;

  @PostConstruct // 이 메서드는 앱이 뜰 때 자동 실행됨
  public void init() {
    insertCodeIfNotExists("ROLE", "TEAM_MEMBER", "기본 사용자");
    insertCodeIfNotExists("ROLE", "TEAM_LEADER", "사용자 및 팀 관리자");
    insertCodeIfNotExists("ROLE", "ADMIN", "운영자 권한");
    insertCodeIfNotExists("ISSUE_STATUS", "PLANNED", "진행 예정");
    insertCodeIfNotExists("ISSUE_STATUS", "IN_PROGRESS", "진행 중");
    insertCodeIfNotExists("ISSUE_STATUS", "COMPLETED", "완료");
    insertCodeIfNotExists("ISSUE_STATUS", "URGENT", "긴급");
    // 필요 시 더 추가
  }

  private void insertCodeIfNotExists(String type, String code, String name) {
    if (!comCodeRepository.existsByCodeTypeAndCode(type, code)) {
      comCodeRepository.save(ComCode.builder()
          .codeType(type)
          .code(code)
          .name(name)
          .build());
    }
  }
}