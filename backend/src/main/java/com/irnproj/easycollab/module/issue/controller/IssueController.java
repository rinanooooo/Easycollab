package com.irnproj.easycollab.module.issue.controller;

import com.irnproj.easycollab.module.issue.dto.IssueRequestDto;
import com.irnproj.easycollab.module.issue.dto.IssueResponseDto;
import com.irnproj.easycollab.module.issue.service.IssueService;
import com.irnproj.easycollab.security.UserPrincipal;
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
public class IssueController {

  private final IssueService issueService;

  // 프로젝트 생성
  @PostMapping
  public ResponseEntity<IssueResponseDto> createIssue(
      @RequestBody @Valid IssueRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(issueService.createIssue(requestDto, userPrincipal.getId()));
  }

  // 프로젝트별 이슈 목록 조회
  @GetMapping("/projects/{projectId}")
  public ResponseEntity<List<IssueResponseDto>> getIssuesByProject(@PathVariable Long projectId) {
    return ResponseEntity.ok(issueService.getIssuesByProject(projectId));
  }

  // 단일 이슈 상세 조회
  @GetMapping("/{id}")
  public ResponseEntity<IssueResponseDto> getIssueById(@PathVariable Long id) {
    return ResponseEntity.ok(issueService.getIssueById(id));
  }

  // 이슈 수정
  @PutMapping("/{id}")
  public ResponseEntity<IssueResponseDto> updateIssue(
      @PathVariable Long id,
      @RequestBody @Valid IssueRequestDto requestDto
  ) {
    return ResponseEntity.ok(issueService.updateIssue(id, requestDto));
  }

  // 이슈 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
    issueService.deleteIssue(id);
    return ResponseEntity.noContent().build(); // 204 No Content
  }
}