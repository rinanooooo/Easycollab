package com.irnproj.easycollab.module.project.controller;

import com.irnproj.easycollab.module.project.dto.ProjectMemberRequestDto;
import com.irnproj.easycollab.module.project.dto.ProjectMemberResponseDto;
import com.irnproj.easycollab.module.project.service.ProjectMemberService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "ProjectMember", description = "프로젝트 멤버 관리 API")
@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMemberController {

  private final ProjectMemberService projectMemberService;

  @Operation(summary = "프로젝트 멤버 추가", description = "지정된 프로젝트에 새로운 멤버를 추가합니다.")
  @PostMapping
  public ResponseEntity<ProjectMemberResponseDto> addProjectMember(
      @PathVariable Long projectId,
      @RequestBody @Valid ProjectMemberRequestDto dto) {
    return ResponseEntity.ok(projectMemberService.addProjectMember(projectId, dto));
  }

  @Operation(summary = "프로젝트 멤버 목록 조회", description = "해당 프로젝트에 속한 모든 멤버 목록을 조회합니다.")
  @GetMapping
  public ResponseEntity<List<ProjectMemberResponseDto>> getProjectMembers(@PathVariable Long projectId) {
    return ResponseEntity.ok(projectMemberService.getProjectMembers(projectId));
  }

  @Operation(summary = "프로젝트 멤버 역할 수정", description = "지정된 멤버의 역할 코드를 변경합니다.")
  @PutMapping("/{memberId}")
  public ResponseEntity<ProjectMemberResponseDto> updateMemberRole(
      @PathVariable Long projectId,
      @PathVariable Long memberId,
      @RequestBody Map<String, Long> body) {
    Long roleCodeId = body.get("roleCodeId");
    return ResponseEntity.ok(projectMemberService.updateMemberRole(projectId, memberId, roleCodeId));
  }

  @Operation(summary = "프로젝트 멤버 제거", description = "프로젝트에서 특정 멤버를 제거합니다.")
  @DeleteMapping("/{memberId}")
  public ResponseEntity<Void> removeProjectMember(
      @PathVariable Long projectId,
      @PathVariable Long memberId) {
    projectMemberService.removeProjectMember(projectId, memberId);
    return ResponseEntity.noContent().build();
  }
}