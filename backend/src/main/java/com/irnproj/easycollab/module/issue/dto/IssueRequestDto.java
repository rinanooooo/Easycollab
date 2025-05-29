package com.irnproj.easycollab.module.issue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IssueRequestDto {
  private String title;         // 이슈 제목
  private String content;       // 이슈 내용
  private String statusCode;    // ex: "OPEN", "IN_PROGRESS", "URGENT", "COMPLETED" 등
  private Long projectId;       // 해당 이슈가 속한 프로젝트 ID
  private Long reporterId;      // 이슈 작성자 (로그인 유저 ID or 선택)
  private Long assigneeId;      // 이슈 담당자 (선택 가능)
}
