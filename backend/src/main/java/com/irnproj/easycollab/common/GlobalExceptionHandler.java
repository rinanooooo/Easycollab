package com.irnproj.easycollab.common;

import com.irnproj.easycollab.common.dto.ErrorResponseDto;
import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException ex, HttpServletRequest request) {
    ErrorCode code = ex.getErrorCode();
    return ResponseEntity.status(code.getStatus()).body(
        ErrorResponseDto.builder()
            .status(code.getStatus().value())
            .message(code.getMessage())
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build()
    );
  }

  private ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status, String code, String message, HttpServletRequest request) {
    return ResponseEntity.status(status).body(
        ErrorResponseDto.builder()
            .status(status.value())
            .message(message)
            .path(request.getRequestURI())
            .timestamp(LocalDateTime.now())
            .build()
    );
  }
}