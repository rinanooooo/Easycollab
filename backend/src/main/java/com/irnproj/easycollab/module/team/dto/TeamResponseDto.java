package com.irnproj.easycollab.module.team.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TeamResponseDto {
  private Long id;
  private String name;
  private String description;
  private String createdBy;
  private LocalDateTime createdAt;
}