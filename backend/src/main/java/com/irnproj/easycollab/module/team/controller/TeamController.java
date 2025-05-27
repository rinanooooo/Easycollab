package com.irnproj.easycollab.module.team.controller;

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

  @PostMapping
  public ResponseEntity<TeamResponseDto> createTeam(@RequestBody TeamRequestDto requestDto,
                                                    @AuthenticationPrincipal UserPrincipal principal) {
    return ResponseEntity.ok(teamService.createTeam(requestDto, principal.getId()));
  }

  @GetMapping
  public ResponseEntity<List<TeamResponseDto>> getTeams() {
    return ResponseEntity.ok(teamService.getAllTeams());
  }
}