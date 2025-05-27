package com.irnproj.easycollab.common;

import com.irnproj.easycollab.common.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponseDto> handleIllegalArgument(
      IllegalArgumentException e,
      HttpServletRequest request
  ) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new ErrorResponseDto(
            HttpStatus.BAD_REQUEST.value(),
            e.getMessage(),
            request.getRequestURI(),
            LocalDateTime.now()
        ));
  }
}
