package com.irnproj.easycollab.module.team.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TeamRequestDto {
  private String teamName;
  private String description;

  public void update(String teamName, String description) {
    this.teamName = teamName;
    this.description = description;
  }
}