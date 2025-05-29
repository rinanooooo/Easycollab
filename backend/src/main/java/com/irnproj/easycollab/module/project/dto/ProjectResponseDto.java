package com.irnproj.easycollab.module.project.dto;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.entity.Team;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {
  private Long id;
  private String name;
  private String description;
  private String createdBy;
  private Long teamId;
  private String statusCode;
  private String statusName;
  private LocalDate startDate;
  private LocalDate endDate;

  public static ProjectResponseDto fromEntity(Project project) {
    ComCode status = project.getStatus(); // status 선언

    return ProjectResponseDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .createdBy(project.getCreatedBy().getUsername())
        .teamId(project.getTeam().getId())
        .statusCode(status != null ? status.getCode() : null)
        .statusName(status != null ? status.getName() : null)
        .startDate(project.getStartDate())
        .endDate(project.getEndDate())
        .build();
  }
}