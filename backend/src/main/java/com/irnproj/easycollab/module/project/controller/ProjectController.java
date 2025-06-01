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
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  // 프로젝트 생성
  @PostMapping("/teams/{teamId}")
  public ResponseEntity<ProjectResponseDto> createProject(
      @PathVariable Long teamId,
      @RequestBody ProjectRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    return ResponseEntity.ok(projectService.createProject(teamId, requestDto, userPrincipal.getId()));
  }

  // 전체 프로젝트 목록 조회
  @GetMapping
  public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
    return ResponseEntity.ok(projectService.getAllProjects());
  }

  // 프로젝트명 검색
  @GetMapping("/search")
  public ResponseEntity<List<ProjectResponseDto>> searchProjects(@RequestParam String keyword) {
    return ResponseEntity.ok(projectService.searchProjectsByName(keyword));
  }

  // 단일 프로젝트 상세 조회 (누구나 조회 가능)
  @GetMapping("/{id}")
  public ResponseEntity<ProjectResponseDto> getProjectById(
      @PathVariable Long id,
      @AuthenticationPrincipal UserPrincipal userPrincipal // 로그인 정보 Optional
  ) {
    return ResponseEntity.ok(projectService.getProjectById(id, userPrincipal != null ? userPrincipal.getId() : null));
  }

  // 내 프로젝트 목록 조회
  @GetMapping("/me")
  public ResponseEntity<List<ProjectResponseDto>> getMyProjects(
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    return ResponseEntity.ok(projectService.getMyProjects(userPrincipal.getId()));
  }

  // 프로젝트 수정
  @PutMapping("/{id}")
  public ResponseEntity<ProjectResponseDto> updateProject(
      @PathVariable Long id,
      @RequestBody ProjectRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    return ResponseEntity.ok(projectService.updateProject(id, requestDto, userPrincipal.getId()));
  }

  // 프로젝트 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(
      @PathVariable Long id,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    projectService.deleteProject(id, userPrincipal.getId());
    return ResponseEntity.noContent().build();
  }
}
