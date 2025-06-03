package com.irnproj.easycollab.module.project.dto;

import com.irnproj.easycollab.module.project.entity.Project;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponseDto {

  private Long id;

  @JsonProperty("projectName")
  private String name;

  private String description;

  private String statusName;

  private Long teamId;
  private String teamName;

  public static ProjectResponseDto from(Project project) {
    return ProjectResponseDto.builder()
        .id(project.getId())
        .name(project.getName())
        .description(project.getDescription())
        .statusName(project.getStatus().getName())
        .teamId(project.getTeam().getId())
        .teamName(project.getTeam().getTeamName())
        .build();
  }
}