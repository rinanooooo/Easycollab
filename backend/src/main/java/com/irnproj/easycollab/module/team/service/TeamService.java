package com.irnproj.easycollab.module.team.service;

import com.irnproj.easycollab.module.team.dto.TeamRequestDto;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.team.entity.TeamMember;
import com.irnproj.easycollab.module.team.repository.TeamRepository;
import com.irnproj.easycollab.module.team.repository.TeamMemberRepository;
import com.irnproj.easycollab.module.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TeamService {

  private final TeamRepository teamRepository;
  private final TeamMemberRepository teamMemberRepository;

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
    TeamMember member = TeamMember.builder()
        .team(team)
        .user(user)
        .role("LEADER") // 팀장 역할
        .joinedAt(LocalDateTime.now())
        .build();

    teamMemberRepository.save(member);

    // 3. 응답 반환
    return new TeamResponseDto(
        team.getId(),
        team.getName(),
        team.getDescription(),
        user.getNickname()
    );
  }
}
