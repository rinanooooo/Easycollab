package com.irnproj.easycollab.module.team.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TeamResponseDto {
  private Long id;                   // 팀 ID
  private String name;               // 팀 이름
  private String description;        // 팀 설명
  private String ownerNickname;      // 팀장 닉네임
  private Boolean isMyTeam;          // 내가 속한 팀 여부 (마이페이지용은 항상 true)

  private int memberCount;           // 팀원 수
  private String myRole;             // 내 역할 (팀장/팀원 등)
  private LocalDateTime createdAt;   // 팀 생성일
}