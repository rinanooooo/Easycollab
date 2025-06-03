package com.irnproj.easycollab.module.issue.dto;

import com.irnproj.easycollab.module.issue.entity.Issue;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueSummaryDto {
  private Long issueId;
  private String title;
  private String statusName;

  public static IssueSummaryDto from(Issue issue) {
    return IssueSummaryDto.builder()
        .issueId(issue.getId())
        .title(issue.getTitle())
        .statusName(issue.getStatus().getName())
        .build();
  }
}