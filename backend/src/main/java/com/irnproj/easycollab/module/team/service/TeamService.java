package com.irnproj.easycollab.module.team.service;

import com.irnproj.easycollab.module.team.dto.TeamRequestDto;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.entity.Team;
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

  public TeamResponseDto createTeam(TeamRequestDto requestDto, Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    Team team = new Team(requestDto.getName(), requestDto.getDescription(), user);
    Team saved = teamRepository.save(team);
    return new TeamResponseDto(saved.getId(), saved.getName(), saved.getDescription(), user.getUserName(), saved.getCreatedAt());
  }

  public List<TeamResponseDto> getAllTeams() {
    return teamRepository.findAll().stream()
        .map(t -> new TeamResponseDto(
            t.getId(), t.getName(), t.getDescription(), t.getCreatedBy().getUserName(), t.getCreatedAt()
        )).collect(Collectors.toList());
  }
}
