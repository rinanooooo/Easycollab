package com.irnproj.easycollab.module.issue.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class IssueResponseDto {

  // 이슈 고유 ID
  private Long id;

  // 이슈 제목
  private String title;

  // 이슈 본문 내용
  private String content;

  // 이슈 상태 코드 (예: "IN_PROGRESS")
  private String statusCode;

  // 이슈 상태 이름 (예: "진행 중")
  private String statusName;

  // 작성자 닉네임
  private String reporterName;

  // 담당자 닉네임 (없을 수도 있음)
  private String assigneeName;

  // 소속된 프로젝트 이름
  private String projectName;

  // 생성일시
  private LocalDateTime createdAt;

  // 마지막 수정일시
  private LocalDateTime updatedAt;

  // 이슈 시작 예정일 또는 실제 시작일
  private LocalDate startDate;

  // 이슈 마감일 또는 목표 종료일
  private LocalDate endDate;

}