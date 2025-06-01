package com.irnproj.easycollab.module.team.service;

import com.irnproj.easycollab.module.team.dto.TeamMemberResponseDto;
import com.irnproj.easycollab.module.team.dto.TeamRequestDto;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.entity.TeamMember;
import com.irnproj.easycollab.module.team.repository.TeamMemberRepository;
import com.irnproj.easycollab.module.team.repository.TeamRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final UserRepository userRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final TeamRepository teamRepository;

  // 팀 생성
  public List<TeamResponseDto> getMyTeams(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

    List<TeamMember> memberships = teamMemberRepository.findByUser(user); // EntityGraph 적용됨

    return memberships.stream()
        .map(tm -> {
          Team team = tm.getTeam();

          return TeamResponseDto.builder()
              .id(team.getId())
              .name(team.getName())
              .description(team.getDescription())
              .ownerNickname(team.getOwner().getNickname())
              .isMyTeam(true)
              .memberCount(team.getTeamMembers() != null ? team.getTeamMembers().size() : 0)
              .myRole(tm.getRole() != null ? tm.getRole().getName() : "팀원")
              .createdAt(team.getCreatedAt())
              .build();
        })
        .collect(Collectors.toList());
  }

  // 팀 전체 목록 조회
  public List<TeamResponseDto> getAllTeams() {
    return teamRepository.findAll().stream()
        .map(team -> TeamResponseDto.builder()
            .id(team.getId())
            .name(team.getName())
            .description(team.getDescription())
            .ownerNickname(team.getOwner().getNickname())
            .isMyTeam(false) // 전체 팀 조회이므로 false
            .memberCount(team.getTeamMembers() != null ? team.getTeamMembers().size() : 0)
            .myRole(null)
            .createdAt(team.getCreatedAt())
            .build())
        .collect(Collectors.toList());
  }

  // 팀명 기반 검색
  public List<TeamResponseDto> searchTeamsByName(String keyword) {
    List<Team> teams = teamRepository.findByNameContainingIgnoreCase(keyword);

    return teams.stream()
        .map(team -> TeamResponseDto.builder()
            .id(team.getId())
            .name(team.getName())
            .description(team.getDescription())
            .ownerNickname(team.getOwner().getNickname())
            .isMyTeam(false)
            .memberCount(team.getTeamMembers() != null ? team.getTeamMembers().size() : 0)
            .myRole(null)
            .createdAt(team.getCreatedAt())
            .build())
        .collect(Collectors.toList());
  }

  // 단일 팀 상세 조회
  public TeamResponseDto getTeamById(Long teamId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

    return TeamResponseDto.builder()
        .id(team.getId())
        .name(team.getName())
        .description(team.getDescription())
        .ownerNickname(team.getOwner().getNickname())
        .isMyTeam(false) // 내 팀 여부 판단은 다음 단계에서
        .memberCount(team.getTeamMembers() != null ? team.getTeamMembers().size() : 0)
        .myRole(null)
        .createdAt(team.getCreatedAt())
        .build();
  }

  // 내가 속한 팀만 상세 조회 가능
  public TeamResponseDto getMyTeamById(Long teamId, Long userId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));

    TeamMember member = teamMemberRepository.findByTeamAndUser(team, user)
        .orElseThrow(() -> new IllegalStateException("해당 팀의 구성원이 아닙니다."));

    return TeamResponseDto.builder()
        .id(team.getId())
        .name(team.getName())
        .description(team.getDescription())
        .ownerNickname(team.getOwner().getNickname())
        .isMyTeam(true)
        .memberCount(team.getTeamMembers() != null ? team.getTeamMembers().size() : 0)
        .myRole(member.getRole() != null ? member.getRole().getName() : "팀원")
        .createdAt(team.getCreatedAt())
        .build();
  }

  // 팀 수정 (팀장 권한)
  public TeamResponseDto updateTeam(Long teamId, TeamRequestDto requestDto, Long userId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

    // 팀장 권한 확인
    if (!team.getOwner().getId().equals(userId)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "팀장만 수정할 수 있습니다.");
    }

    team.update(requestDto.getName(), requestDto.getDescription());

    return TeamResponseDto.builder()
        .id(team.getId())
        .name(team.getName())
        .description(team.getDescription())
        .ownerNickname(team.getOwner().getNickname())
        .isMyTeam(true)
        .memberCount(team.getTeamMembers() != null ? team.getTeamMembers().size() : 0)
        .myRole("팀장")
        .createdAt(team.getCreatedAt())
        .build();
  }

  // 팀 삭제 (팀장 권한)
  public void deleteTeam(Long teamId, Long userId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

    if (!team.getOwner().getId().equals(userId)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "팀장만 삭제할 수 있습니다.");
    }

    teamRepository.delete(team);
  }

  // 팀원 목록 조회
  public List<TeamMemberResponseDto> getTeamMembers(Long teamId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

    List<TeamMember> members = team.getTeamMembers();

    return members.stream()
        .map(member -> TeamMemberResponseDto.builder()
            .userId(member.getUser().getId())
            .nickname(member.getUser().getNickname())
            .roleName(member.getRole() != null ? member.getRole().getName() : "팀원")
            .build())
        .collect(Collectors.toList());
  }
}
