package com.irnproj.easycollab.module.issue.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class IssueRequestDto {
  private String title;
  private String content;
  private String status;      // ex) "예정", "진행중", "완료"
  private Long assigneeId;
  private LocalDate startDate;
  private LocalDate endDate;
  private boolean isPinned;
}
