package com.irnproj.easycollab.module.team.dto;

import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.team.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
public class TeamResponseDto {

  private Long teamId;                    // 팀 ID
  private String teamName;                // 팀 이름
  private String description;             // 팀 설명
  private String ownerName;           // 팀장 닉네임

  private boolean isMyTeam;               // 내가 속한 팀 여부
  private int memberCount;                // 팀원 수

  private String myRole;                  // 내 역할 (팀장/팀원 등)
  private LocalDateTime createdAt;        // 팀 생성일

  private List<ProjectResponseDto> projects; // 팀에 속한 프로젝트 목록

  public static TeamResponseDto of(Team team, Long userId) {
    // 내가 속한 팀인지 확인
    boolean isMyTeam = team.getTeamMembers().stream()
        .anyMatch(member -> member.getUser().getId().equals(userId));

    // 내 역할 구하기 (null-safe)
    String myRole = team.getTeamMembers().stream()
        .filter(member -> member.getUser().getId().equals(userId))
        .map(member -> member.getRole() != null ? member.getRole().getName() : "팀원")
        .findFirst()
        .orElse("팀원");

    return TeamResponseDto.builder()
        .teamId(team.getId())
        .teamName(team.getTeamName())
        .description(team.getDescription())
        .ownerName(team.getOwner().getUsername())
        .isMyTeam(isMyTeam)
        .memberCount(team.getTeamMembers().size())
        .myRole(myRole)
        .createdAt(team.getCreatedAt())
        .projects(team.getProjects().stream()
            .map(ProjectResponseDto::from) // from(Project) 정적 메서드가 필요함
            .collect(Collectors.toList()))
        .build();
  }
}