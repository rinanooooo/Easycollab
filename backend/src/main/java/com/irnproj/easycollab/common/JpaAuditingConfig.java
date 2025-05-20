package com.irnproj.easycollab.common;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration // Spring 설정 클래스
@EnableJpaAuditing // JPA에서 날짜 자동 기록 기능 ON
public class JpaAuditingConfig {
  // @CreatedDate, @LastModifiedDate가 작동하기 위한 설정.
}