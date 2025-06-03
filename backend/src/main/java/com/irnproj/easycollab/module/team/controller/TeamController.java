// src/module/team/controller/TeamController.java
package com.irnproj.easycollab.module.team.controller;

import com.irnproj.easycollab.module.team.dto.TeamMemberResponseDto;
import com.irnproj.easycollab.module.team.dto.TeamRequestDto;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.service.TeamService;
import com.irnproj.easycollab.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
@Tag(name = "Team", description = "팀 관련 API")
public class TeamController {

  private final TeamService teamService;

  @Operation(summary = "팀 생성", description = "팀 이름과 설명을 입력하여 팀을 생성합니다. 생성자는 자동으로 팀장으로 등록됩니다.")
  @PostMapping
  public ResponseEntity<Long> createTeam(
      @RequestBody TeamRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    Long teamId = teamService.createTeam(requestDto, userPrincipal.getId());
    return ResponseEntity.ok(teamId);
  }

  @Operation(summary = "팀 수정", description = "해당 팀의 이름과 설명을 수정합니다. 팀장만 수정할 수 있습니다.")
  @PutMapping("/{id}")
  public ResponseEntity<Void> updateTeam(
      @PathVariable Long id,
      @RequestBody TeamRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    teamService.updateTeam(id, requestDto, userPrincipal.getId());
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "팀 삭제", description = "해당 팀을 삭제합니다. 팀장만 삭제할 수 있습니다.")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTeam(
      @PathVariable Long id,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    teamService.deleteTeam(id, userPrincipal.getId());
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "전체 팀 조회", description = "전체 팀 목록을 조회합니다. onlyMyTeam=true 쿼리 파라미터로 내 팀만 필터링 가능합니다.")
  @GetMapping
  public ResponseEntity<List<TeamResponseDto>> getAllTeams(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestParam(required = false, defaultValue = "false") boolean onlyMyTeam) {
    return ResponseEntity.ok(teamService.getAllTeams(userPrincipal.getId(), onlyMyTeam));
  }

  @Operation(summary = "단일 팀 조회", description = "팀 ID로 단일 팀 정보를 조회합니다.")
  @GetMapping("/{id}")
  public ResponseEntity<TeamResponseDto> getTeamById(@PathVariable Long id) {
    return ResponseEntity.ok(teamService.getTeamById(id));
  }

  @Operation(summary = "팀원 목록 조회", description = "해당 팀의 모든 팀원 목록을 조회합니다.")
  @GetMapping("/{id}/members")
  public ResponseEntity<List<TeamMemberResponseDto>> getTeamMembers(@PathVariable Long id) {
    return ResponseEntity.ok(teamService.getTeamMembers(id));
  }
}