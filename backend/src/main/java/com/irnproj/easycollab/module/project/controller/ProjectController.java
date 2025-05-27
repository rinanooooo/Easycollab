package com.irnproj.easycollab.module.project.controller;

import com.irnproj.easycollab.module.project.dto.ProjectRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams/{teamId}/projects")
public class ProjectController {

  private final ProjectService projectService;

  @PostMapping
  public ResponseEntity<ProjectResponseDto> createProject(
      @PathVariable Long teamId,
      @RequestBody ProjectRequestDto request
  ) {
    ProjectResponseDto response = projectService.createProject(teamId, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<ProjectResponseDto>> getProjects(
      @PathVariable Long teamId
  ) {
    List<ProjectResponseDto> projects = projectService.getProjectsByTeamId(teamId);
    return ResponseEntity.ok(projects);
  }
}