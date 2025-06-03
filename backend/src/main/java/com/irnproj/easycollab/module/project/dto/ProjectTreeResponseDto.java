package com.irnproj.easycollab.module.project.dto;

import com.irnproj.easycollab.module.issue.dto.IssueSummaryDto;
import com.irnproj.easycollab.module.project.entity.Project;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectTreeResponseDto {
  private Long projectId;
  private String projectName;
  private List<IssueSummaryDto> issues;

  public static ProjectTreeResponseDto from(Project project) {
    return ProjectTreeResponseDto.builder()
        .projectId(project.getId())
        .projectName(project.getName())
        .issues(
            project.getIssues().stream()
                .map(IssueSummaryDto::from)
                .collect(Collectors.toList())
        )
        .build();
  }
}
