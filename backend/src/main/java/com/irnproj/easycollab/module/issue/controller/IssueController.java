package com.irnproj.easycollab.module.issue.controller;

import com.irnproj.easycollab.module.issue.dto.IssueRequestDto;
import com.irnproj.easycollab.module.issue.dto.IssueResponseDto;
import com.irnproj.easycollab.module.issue.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {

  private final IssueService issueService;

  // 이슈 생성
  @PostMapping
  public ResponseEntity<IssueResponseDto> createIssue(@RequestBody IssueRequestDto requestDto) {
    IssueResponseDto response = issueService.createIssue(requestDto);
    return ResponseEntity.ok(response);
  }

  // 이슈 전체 조회
  @GetMapping
  public ResponseEntity<List<IssueResponseDto>> getAllIssues() {
    List<IssueResponseDto> issues = issueService.getAllIssues();
    return ResponseEntity.ok(issues);
  }

  // 이슈 단건 조회
  @GetMapping("/{id}")
  public ResponseEntity<IssueResponseDto> getIssueById(@PathVariable Long id) {
    IssueResponseDto issue = issueService.getIssueById(id);
    return ResponseEntity.ok(issue);
  }

  // 이슈 수정
  @PutMapping("/{id}")
  public ResponseEntity<IssueResponseDto> updateIssue(@PathVariable Long id,
                                                      @RequestBody IssueRequestDto requestDto) {
    IssueResponseDto updated = issueService.updateIssue(id, requestDto);
    return ResponseEntity.ok(updated);
  }

  // 이슈 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteIssue(@PathVariable Long id) {
    issueService.deleteIssue(id);
    return ResponseEntity.noContent().build(); // 204 No Content
  }

}