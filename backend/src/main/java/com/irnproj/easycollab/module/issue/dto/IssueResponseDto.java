package com.irnproj.easycollab.module.issue.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IssueResponseDto {
  private Long id;
  private String title;
  private String content;
  private String statusName;     // 예: "종료", "여유", "긴급"
  private Long projectId;
  private String projectName;
  private Long reporterId;
  private String reporterName;
}


