package com.irnproj.easycollab.module.team.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class TeamRequestDto {
  private String name;
  private String description;

  public void update(String name, String description) {
    this.name = name;
    this.description = description;
  }
}