package com.irnproj.easycollab.module.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectResponseDto {
  private Long id;
  private String name;
  private String description;
  private Long teamId;
}