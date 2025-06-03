package com.irnproj.easycollab.module.issue.controller;

import com.irnproj.easycollab.module.issue.dto.IssueRequestDto;
import com.irnproj.easycollab.module.issue.dto.IssueResponseDto;
import com.irnproj.easycollab.module.issue.service.IssueService;
import com.irnproj.easycollab.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
@Tag(name = "Issue", description = "이슈 관련 API")
public class IssueController {

  private final IssueService issueService;

  @PostMapping("/project/{projectId}")
  @Operation(summary = "이슈 생성", description = "프로젝트 ID와 이슈 정보를 입력하여 새로운 이슈를 생성합니다.")
  public ResponseEntity<IssueResponseDto> createIssue(
      @PathVariable Long projectId,
      @Valid @RequestBody IssueRequestDto requestDto
  ) {
    IssueResponseDto responseDto = issueService.createIssue(projectId, requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
  }

  @GetMapping("/{issueId}")
  @Operation(summary = "이슈 단건 조회", description = "이슈 ID로 이슈 상세 정보를 조회합니다.")
  public ResponseEntity<IssueResponseDto> getIssue(@PathVariable Long issueId) {
    return ResponseEntity.ok(issueService.getIssue(issueId));
  }

  @PutMapping("/{issueId}")
  @Operation(summary = "이슈 수정", description = "이슈 ID로 기존 이슈를 수정합니다.")
  public ResponseEntity<IssueResponseDto> updateIssue(
      @PathVariable Long issueId,
      @Valid @RequestBody IssueRequestDto requestDto
  ) {
    return ResponseEntity.ok(issueService.updateIssue(issueId, requestDto));
  }

  @DeleteMapping("/{issueId}")
  @Operation(summary = "이슈 삭제", description = "이슈 ID로 이슈를 삭제합니다.")
  public ResponseEntity<Void> deleteIssue(@PathVariable Long issueId) {
    issueService.deleteIssue(issueId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/project/{projectId}")
  @Operation(summary = "프로젝트 내 이슈 목록 조회", description = "특정 프로젝트에 포함된 모든 이슈를 조회합니다.")
  public ResponseEntity<List<IssueResponseDto>> getIssuesByProject(@PathVariable Long projectId) {
    return ResponseEntity.ok(issueService.getIssuesByProject(projectId));
  }
}