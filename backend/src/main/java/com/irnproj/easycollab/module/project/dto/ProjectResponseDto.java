package com.irnproj.easycollab.module.project.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDto {
  // 프로젝트 고유 식별자
  private Long id;

  // 프로젝트 이름
  private String name;

  // 프로젝트 설명
  private String description;

  // 소속 팀 이름
  private String teamName;

  // 로그인한 사용자가 이 프로젝트에 참여 중인지 여부
  private boolean isMyProject;

  // 로그인한 사용자의 프로젝트 내 역할 (예: "팀장", "참여자")
  private String myRole;

  // 프로젝트 생성일 (등록일)
  private LocalDateTime createdAt;

  // 프로젝트 마지막 수정일
  private LocalDateTime updatedAt;

  // 프로젝트 시작일 (예정 상태일 경우 null 가능)
  private LocalDate startDate;

  // 프로젝트 종료일 (완료된 프로젝트에만 존재할 수 있음)
  private LocalDate endDate;

  // 프로젝트 상태 코드 (예: "PLANNED", "IN_PROGRESS", "COMPLETED")
  private String statusCode;

  // 프로젝트 상태 이름 (예: "예정", "진행 중", "완료")
  private String statusName;
}