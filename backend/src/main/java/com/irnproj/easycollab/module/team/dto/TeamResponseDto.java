package com.irnproj.easycollab.module.team.dto;

import com.irnproj.easycollab.module.team.entity.Team;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamResponseDto {
  private Long id;
  private String name;
  private String description;
  private String ownerNickname;
  private Boolean isMyTeam;

  public static TeamResponseDto fromEntity(Team team) {
    return TeamResponseDto.builder()
        .id(team.getId())
        .name(team.getName())
        .description(team.getDescription())
        .ownerNickname(team.getOwner().getNickname())
        .isMyTeam(true)
        .build();
  }
}