package com.irnproj.easycollab.module.issue.controller;

import com.irnproj.easycollab.module.issue.dto.IssueRequestDto;
import com.irnproj.easycollab.module.issue.dto.IssueResponseDto;
import com.irnproj.easycollab.module.issue.service.IssueService;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/issues")
public class IssueController {

  private final IssueService issueService;

  // 이슈 생성
  @PostMapping
  public ResponseEntity<IssueResponseDto> createIssue(
      @RequestBody IssueRequestDto requestDto,
      @AuthenticationPrincipal UserPrincipal userPrincipal) {
    IssueResponseDto response = issueService.createIssue(requestDto, userPrincipal);
    return ResponseEntity.ok(response);
  }

  // 전체 이슈 조회
  @GetMapping
  public ResponseEntity<List<IssueResponseDto>> getAllIssues() {
    return ResponseEntity.ok(issueService.getAllIssues());
  }

  // 단일 이슈 조회
  @GetMapping("/{id}")
  public ResponseEntity<IssueResponseDto> getIssueById(@PathVariable Long id) {
    return ResponseEntity.ok(issueService.getIssueById(id));
  }

  // 이슈 수정
  @PutMapping("/{id}")
  public ResponseEntity<IssueResponseDto> updateIssue(
      @PathVariable Long id,
      @RequestBody IssueRequestDto requestDto) {
    return ResponseEntity.ok(issueService.updateIssue(id, requestDto));
  }

  // 이슈 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
    issueService.deleteIssue(id);
    return ResponseEntity.noContent().build();
  }
}