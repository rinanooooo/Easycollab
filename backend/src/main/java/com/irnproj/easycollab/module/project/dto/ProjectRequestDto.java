package com.irnproj.easycollab.module.project.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectRequestDto {
  private String name;
  private String description;
  private Long teamId;
  private String statusCode;
  private LocalDate startDate;
  private LocalDate endDate;
}