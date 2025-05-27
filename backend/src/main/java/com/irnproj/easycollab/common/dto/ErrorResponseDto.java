package com.irnproj.easycollab.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponseDto {
  private int status;
  private String message;
  private String path;        // 요청 경로
  private LocalDateTime timestamp; // 발생 시각
}