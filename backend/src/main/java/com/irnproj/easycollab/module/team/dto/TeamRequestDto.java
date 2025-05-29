package com.irnproj.easycollab.module.team.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamRequestDto {
  private String name;
  private String description;
  private String statusCode; // ex: "팀장", "팀원"
}