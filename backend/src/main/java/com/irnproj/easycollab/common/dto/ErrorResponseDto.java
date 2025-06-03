package com.irnproj.easycollab.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponseDto {
  private int status;               // 상태
  private String message;           // 에러메세지
  private String path;              // 요청 경로
  private LocalDateTime timestamp;  // 발생 시각
}