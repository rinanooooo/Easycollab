package com.irnproj.easycollab.module.team.service;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import com.irnproj.easycollab.module.team.dto.TeamMemberResponseDto;
import com.irnproj.easycollab.module.team.dto.TeamRequestDto;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.entity.TeamMember;
import com.irnproj.easycollab.module.team.repository.TeamRepository;
import com.irnproj.easycollab.module.team.repository.TeamMemberRepository;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;
  private final ComCodeRepository comCodeRepository;

  @Transactional
  public TeamResponseDto createTeam(TeamRequestDto request, User user) {
    // 1. 팀 생성
    Team team = Team.builder()
        .name(request.getName())
        .description(request.getDescription())
        .owner(user)
        .build();

    teamRepository.save(team);

    // 2. 팀장도 팀 멤버로 등록
    ComCode leaderRole = comCodeRepository.findByCodeTypeAndCode("ROLE", "TEAM_MEMBER")
        .orElseThrow(() -> new IllegalArgumentException("역할 코드 없음"));

    TeamMember member = TeamMember.builder()
        .team(team)
        .user(user)
        .role(leaderRole) // ComCode 타입으로 설정
        .joinedAt(LocalDateTime.now())
        .build();

    teamMemberRepository.save(member);

    // 3. 응답 반환
    return TeamResponseDto.builder()
        .id(team.getId())
        .name(team.getName())
        .description(team.getDescription())
        .ownerNickname(user.getNickname())
        .isMyTeam(true) // 내가 만든 팀이므로 true
        .build();
  }


  // 전체 조회
  @Transactional(readOnly = true)
  public List<TeamResponseDto> getAllTeams() {
    return teamRepository.findAll().stream()
        .map(TeamResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 단일 조회
  @Transactional(readOnly = true)
  public TeamResponseDto getTeamById(Long id) {
    Team team = teamRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));
    return TeamResponseDto.fromEntity(team);
  }

  // 팀 수정
  @Transactional
  public TeamResponseDto updateTeam(Long id, TeamRequestDto dto) {
    Team team = teamRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));

    team.update(dto.getName(), dto.getDescription());
    return TeamResponseDto.fromEntity(team);
  }

  // 팀 삭제
  @Transactional
  public void deleteTeam(Long id) {
    Team team = teamRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("팀을 찾을 수 없습니다."));

    teamRepository.delete(team);
  }

  // 팀원 조회
  @Transactional(readOnly = true)
  public List<TeamMemberResponseDto> getTeamMembers(Long teamId) {
    return teamMemberRepository.findAllByTeamId(teamId).stream()
        .map(TeamMemberResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

}
