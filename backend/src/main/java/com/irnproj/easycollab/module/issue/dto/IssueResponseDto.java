package com.irnproj.easycollab.module.issue.dto;

import com.irnproj.easycollab.module.issue.entity.Issue;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class IssueResponseDto {
  private Long id;
  private String title;
  private String content;
  private String status;
  private String assigneeName;
  private LocalDate startDate;
  private LocalDate endDate;
  private boolean isPinned;

  public static IssueResponseDto of(Issue issue) {
    return IssueResponseDto.builder()
        .id(issue.getId())
        .title(issue.getTitle())
        .content(issue.getContent())
        .status(issue.getStatus().getName())
        .assigneeName(issue.getAssignee() != null ? issue.getAssignee().getNickname() : null)
        .startDate(issue.getStartDate())
        .endDate(issue.getEndDate())
        .isPinned(issue.isPinned())
        .build();
  }
}