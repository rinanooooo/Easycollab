package com.irnproj.easycollab.module.team.controller;

import com.irnproj.easycollab.module.team.dto.TeamRequestDto;
import com.irnproj.easycollab.module.team.dto.TeamResponseDto;
import com.irnproj.easycollab.module.team.service.TeamService;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

  private final TeamService teamService;

  @PostMapping
  public ResponseEntity<TeamResponseDto> createTeam(
      @AuthenticationPrincipal UserPrincipal userPrincipal,
      @RequestBody TeamRequestDto request
  ) {
    User user = userPrincipal.getUser();
    TeamResponseDto response = teamService.createTeam(request, user);
    return ResponseEntity.ok(response);
  }
}