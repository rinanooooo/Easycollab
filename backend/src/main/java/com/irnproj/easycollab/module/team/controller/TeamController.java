package com.irnproj.easycollab.module.team.controller;

import com.irnproj.easycollab.module.team.dto.TeamMemberResponseDto;
import com.irnproj.easycollab.module.team.dto.TeamRequestDto;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.service.TeamService;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

  private final TeamService teamService;

  // 팀 생성
  @PostMapping
  public ResponseEntity<TeamResponseDto> createTeam(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestBody TeamRequestDto request
  ) {
    User user = userPrincipal.getUser();
    TeamResponseDto response = teamService.createTeam(request, user);
    return ResponseEntity.ok(response);
  }

  // 전체 팀 조회
  @GetMapping
  public ResponseEntity<List<TeamResponseDto>> getAllTeams() {
    return ResponseEntity.ok(teamService.getAllTeams());
  }

  // 단일 팀 조회
  @GetMapping("/{id}")
  public ResponseEntity<TeamResponseDto> getTeamById(@PathVariable Long id) {
    return ResponseEntity.ok(teamService.getTeamById(id));
  }

  // 팀 수정
  @PutMapping("/{id}")
  public TeamResponseDto updateTeam(@PathVariable Long id, @RequestBody TeamRequestDto requestDto) {
    return teamService.updateTeam(id, requestDto);
  }

  // 팀 삭제
  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTeam(@PathVariable Long id) {
    teamService.deleteTeam(id);
  }

  // 팀원 조회
  @GetMapping("/{teamId}/members")
  public ResponseEntity<List<TeamMemberResponseDto>> getTeamMembers(@PathVariable Long teamId) {
    return ResponseEntity.ok(teamService.getTeamMembers(teamId));
  }

}