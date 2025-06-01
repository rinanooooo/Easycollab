package com.irnproj.easycollab.module.issue.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class IssueRequestDto {
  private String title;         // 이슈 제목
  private String content;       // 이슈 내용
  private Long projectId;       // 해당 이슈가 속한 프로젝트 ID
  private String statusCode;    // ex: "OPEN", "IN_PROGRESS", "URGENT", "COMPLETED" 등
  private Long assigneeId;      // 담당자 ID
  private LocalDate startDate;  // 시작일
  private LocalDate endDate;    // 종료일
}
