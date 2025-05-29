package com.irnproj.easycollab.module.issue.dto;

import com.irnproj.easycollab.module.issue.entity.Issue;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponseDto {

  private Long id;
  private String title;
  private String content;
  private String statusCode;
  private String statusName;
  private String reporterName;
  private String assigneeName;
  private Long projectId;
  private String projectName;
  private LocalDate createdAt;
  private LocalDate updatedAt;

  public static IssueResponseDto fromEntity(Issue issue) {
    return IssueResponseDto.builder()
        .id(issue.getId())
        .title(issue.getTitle())
        .content(issue.getContent())
        .statusCode(issue.getStatus() != null ? issue.getStatus().getCode() : null)
        .statusName(issue.getStatus() != null ? issue.getStatus().getName() : null)
        .reporterName(issue.getReporter() != null ? issue.getReporter().getUsername() : null)
        .assigneeName(issue.getAssignee() != null ? issue.getAssignee().getUsername() : null)
        .projectId(issue.getProject().getId())
        .projectName(issue.getProject().getName())
        .createdAt(issue.getCreatedAt().toLocalDate())
        .updatedAt(issue.getUpdatedAt().toLocalDate())
        .build();
  }
}


