package com.irnproj.easycollab.module.team.controller;

import com.irnproj.easycollab.module.team.dto.TeamMemberResponseDto;
import com.irnproj.easycollab.module.team.dto.TeamRequestDto;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.service.TeamService;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

  private final TeamService teamService;

  // 팀 생성
  @GetMapping("/my")
  public ResponseEntity<List<TeamResponseDto>> getMyTeams(@AuthenticationPrincipal UserPrincipal userPrincipal) {
    return ResponseEntity.ok(teamService.getMyTeams(userPrincipal.getId()));
  }

  // 전체 팀 목록 조회
  @GetMapping
  public ResponseEntity<List<TeamResponseDto>> getAllTeams() {
    return ResponseEntity.ok(teamService.getAllTeams());
  }

  // 팀명 기반 검색
  @GetMapping("/search")
  public ResponseEntity<List<TeamResponseDto>> searchTeams(@RequestParam String keyword) {
    return ResponseEntity.ok(teamService.searchTeamsByName(keyword));
  }

  //단일 팀 상세 조회
  @GetMapping("/{id}")
  public ResponseEntity<TeamResponseDto> getTeamById(@PathVariable Long id) {
    return ResponseEntity.ok(teamService.getTeamById(id));
  }

  // 내가 속한 팀만 상세 조회 가능
  @GetMapping("/{id}/my")
  public ResponseEntity<TeamResponseDto> getMyTeamById(
      @PathVariable Long id,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    return ResponseEntity.ok(teamService.getMyTeamById(id, userPrincipal.getId()));
  }

  // 팀 수정 (팀장 권한)
  @PutMapping("/{id}")
  public ResponseEntity<TeamResponseDto> updateTeam(
      @PathVariable Long id,
      @RequestBody TeamRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    return ResponseEntity.ok(teamService.updateTeam(id, requestDto, userPrincipal.getId()));
  }

  // 팀 삭제 (팀장 권한)
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTeam(
      @PathVariable Long id,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    teamService.deleteTeam(id, userPrincipal.getId());
    return ResponseEntity.noContent().build();
  }

  // 팀원 목록 조회
  @GetMapping("/{id}/members")
  public ResponseEntity<List<TeamMemberResponseDto>> getTeamMembers(@PathVariable Long id) {
    return ResponseEntity.ok(teamService.getTeamMembers(id));
  }
}
