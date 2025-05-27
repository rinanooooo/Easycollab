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
    insertCode("ROLE", "팀원", "기본 사용자");
    insertCode("ROLE", "팀장", "사용자 및 팀 관리자");
    insertCode("ROLE", "관리자", "운영자 권한");
    // 필요 시 더 추가
  }

  private void insertCode(String type, String code, String name) {
    if (!comCodeRepository.existsByCodeTypeAndCode(type, code)) {
      comCodeRepository.save(ComCode.builder()
          .codeType(type)
          .code(code)
          .name(name)
          .description(name + " 역할입니다.")
          .build());
    }
  }
}