package com.irnproj.easycollab.common.exception;

import com.irnproj.easycollab.common.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}