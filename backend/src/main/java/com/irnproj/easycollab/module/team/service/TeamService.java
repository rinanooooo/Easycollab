package com.irnproj.easycollab.module.team.service;

import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;
  private final UserRepository userRepository;
  private final ComCodeRepository comCodeRepository;
  private final TeamMemberRepository teamMemberRepository;

  /**
   * 팀 생성
   */
  public Long createTeam(TeamRequestDto requestDto, Long userId) {
    User user = findUserById(userId);
    ComCode roleTeamLeader = findComCode("ROLE", "팀장");

    Team team = Team.builder()
        .teamName(requestDto.getTeamName())
        .description(requestDto.getDescription())
        .owner(user)
        .build();

    teamRepository.save(team);

    TeamMember teamMember = TeamMember.builder()
        .user(user)
        .team(team)
        .role(roleTeamLeader)
        .build();

    teamMemberRepository.save(teamMember);
    return team.getId();
  }

  /**
   * 전체 팀 조회 (내 팀 필터링 옵션 포함)
   */
  public List<TeamResponseDto> getAllTeams(Long userId, Boolean onlyMyTeam) {
    List<Team> teams = teamRepository.findAll();

    if (Boolean.TRUE.equals(onlyMyTeam)) {
      List<Long> myTeamIds = teamMemberRepository.findTeamIdsByUserId(userId);
      teams = teams.stream()
          .filter(team -> myTeamIds.contains(team.getId()))
          .collect(Collectors.toList());
    }

    return teams.stream()
        .map(team -> TeamResponseDto.of(team, userId))
        .collect(Collectors.toList());
  }

  /**
   * 단일 팀 조회
   */
  public TeamResponseDto getTeamById(Long teamId) {
    Team team = findTeamById(teamId);
    return TeamResponseDto.of(team, null); // 필요 시 userId 넘겨 isMyTeam 처리 가능
  }

  /**
   * 팀 수정
   */
  public void updateTeam(Long teamId, TeamRequestDto requestDto, Long userId) {
    Team team = findTeamById(teamId);
    checkOwnerAuthorization(team, userId);

    team.update(requestDto.getTeamName(), requestDto.getDescription());
    teamRepository.save(team);
  }


  /**
   * 팀 삭제
   */
  public void deleteTeam(Long teamId, Long userId) {
    Team team = findTeamById(teamId);
    checkOwnerAuthorization(team, userId);

    teamRepository.delete(team);
  }


  /**
   * 팀원 목록 조회 (팀 트리용)
   */
  public List<TeamMemberResponseDto> getTeamMembers(Long teamId) {
    Team team = teamRepository.findById(teamId)
        .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));

    return team.getTeamMembers().stream()
        .map(member -> TeamMemberResponseDto.builder()
            .id(member.getId())
            .nickname(member.getUser().getNickname())
            .roleCode(member.getRole() != null ? member.getRole().getName() : "팀원")
            .build())
        .collect(Collectors.toList());
  }

  // ────────────────────────────
  // private 유틸 메서드
  // ────────────────────────────

  private User findUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
  }

  private Team findTeamById(Long teamId) {
    return teamRepository.findById(teamId)
        .orElseThrow(() -> new CustomException(ErrorCode.TEAM_NOT_FOUND));
  }

  private ComCode findComCode(String codeType, String name) {
    return comCodeRepository.findByCodeTypeAndName(codeType, name)
        .orElseThrow(() -> new CustomException(ErrorCode.COM_CODE_NOT_FOUND));
  }

  private void checkOwnerAuthorization(Team team, Long userId) {
    if (!team.getOwner().getId().equals(userId)) {
      throw new CustomException(ErrorCode.FORBIDDEN);
    }
  }
}