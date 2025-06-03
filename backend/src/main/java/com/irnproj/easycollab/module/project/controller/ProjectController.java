package com.irnproj.easycollab.module.project.controller;

import com.irnproj.easycollab.module.project.dto.ProjectRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectResponseDto;
import com.irnproj.easycollab.module.project.dto.ProjectTreeResponseDto;
import com.irnproj.easycollab.module.project.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

  private final ProjectService projectService;

  @Operation(summary = "전체 프로젝트 조회", description = "등록된 모든 프로젝트 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<List<ProjectResponseDto>> getAllProjects() {
    return ResponseEntity.ok(projectService.getAllProjects());
  }

  @Operation(summary = "프로젝트 검색", description = "프로젝트 이름에 특정 키워드가 포함된 프로젝트를 검색합니다.")
  @GetMapping("/search")
  public ResponseEntity<List<ProjectResponseDto>> searchProjects(@RequestParam String name) {
    return ResponseEntity.ok(projectService.searchProjectsByName(name));
  }

  @Operation(summary = "단일 프로젝트 조회", description = "프로젝트 ID로 프로젝트 상세 정보를 조회합니다.")
  @GetMapping("/{id}")
  public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable Long id) {
    return ResponseEntity.ok(projectService.getProjectById(id));
  }

  @Operation(summary = "내 프로젝트 조회", description = "현재 로그인 사용자가 참여 중인 프로젝트 목록을 조회합니다.")
  @GetMapping("/my")
  public ResponseEntity<List<ProjectResponseDto>> getMyProjects(@RequestParam Long userId) {
    return ResponseEntity.ok(projectService.getProjectsByUserId(userId));
  }

  @Operation(summary = "프로젝트-이슈 트리 조회", description = "프로젝트별 이슈 목록을 트리 구조로 조회합니다.")
  @GetMapping("/tree")
  public ResponseEntity<List<ProjectTreeResponseDto>> getProjectTree() {
    return ResponseEntity.ok(projectService.getProjectIssueTree());
  }

  @Operation(summary = "프로젝트 생성", description = "프로젝트 이름, 설명, 팀 ID, 생성자 ID, 상태 코드를 입력하여 새 프로젝트를 생성합니다.")
  @PostMapping
  public ResponseEntity<ProjectResponseDto> createProject(@RequestBody @Valid ProjectRequestDto requestDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(requestDto));
  }

  @Operation(summary = "프로젝트 수정", description = "프로젝트 ID에 해당하는 프로젝트의 이름, 설명, 상태를 수정합니다.")
  @PutMapping("/{id}")
  public ResponseEntity<ProjectResponseDto> updateProject(
      @PathVariable Long id,
      @RequestBody @Valid ProjectRequestDto requestDto) {
    return ResponseEntity.ok(projectService.updateProject(id, requestDto));
  }

  @Operation(summary = "프로젝트 삭제", description = "프로젝트 ID에 해당하는 프로젝트를 삭제합니다.")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
    projectService.deleteProject(id);
    return ResponseEntity.noContent().build();
  }
}