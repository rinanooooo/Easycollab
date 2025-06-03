package com.irnproj.easycollab.module.comCode.controller;

import com.irnproj.easycollab.module.comCode.dto.ComCodeResponseDto;
import com.irnproj.easycollab.module.comCode.service.ComCodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/com-codes")
@RequiredArgsConstructor
public class ComCodeController {

  private final ComCodeService comCodeService;

  @Operation(summary = "코드 타입별 전체 코드 목록 조회")
  @GetMapping
  public ResponseEntity<List<ComCodeResponseDto>> getComCodes(
      @Parameter(description = "코드 타입 (예: ROLE, ISSUE_STATUS)")
      @RequestParam String type) {
    return ResponseEntity.ok(comCodeService.getComCodesByType(type));
  }

  @Operation(summary = "단일 코드 조회")
  @GetMapping("/{codeType}/{code}")
  public ResponseEntity<ComCodeResponseDto> getComCode(
      @Parameter(description = "코드 타입", example = "ROLE")
      @PathVariable String codeType,
      @Parameter(description = "코드 값", example = "TEAM_MEMBER")
      @PathVariable String code) {
    return ResponseEntity.ok(comCodeService.getComCode(codeType, code));
  }
}