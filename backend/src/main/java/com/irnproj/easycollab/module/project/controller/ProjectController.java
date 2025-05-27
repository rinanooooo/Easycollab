package com.irnproj.easycollab.module.project.controller;

import com.irnproj.easycollab.module.project.dto.ProjectRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.project.service.ProjectService;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class ProjectController {

  private final ProjectService projectService;

  // 프로젝트 생성
  @PostMapping("/teams/{teamId}")
  public ResponseEntity<ProjectResponseDto> createProject(
      @PathVariable Long teamId,
      @RequestBody ProjectRequestDto request,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    ProjectResponseDto response = projectService.createProject(teamId, request, userPrincipal.getId());
    return ResponseEntity.ok(response);
  }

  // 전체 프로젝트 조회
  @GetMapping
  public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
    return ResponseEntity.ok(projectService.getAllProjects());
  }

  // 특정 팀의 프로젝트 조회
  @GetMapping("/teams/{teamId}/list")
  public ResponseEntity<List<ProjectResponseDto>> getProjectsByTeam(@PathVariable Long teamId) {
    return ResponseEntity.ok(projectService.getProjectsByTeam(teamId));
  }

  // 사용자의 프로젝트
  @GetMapping("/me")
  public ResponseEntity<List<ProjectResponseDto>> getMyProjects(
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    return ResponseEntity.ok(projectService.getMyProjects(userPrincipal.getId()));
  }
}