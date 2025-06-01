package com.irnproj.easycollab.module.project.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ProjectRequestDto {
  private String name;
  private String description;
  private LocalDate startDate;
  private LocalDate endDate;
  private String statusCode; // ex: "PLANNED"
}
