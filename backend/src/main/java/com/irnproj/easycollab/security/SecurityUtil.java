package com.irnproj.easycollab.security;

import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

  /**
   * 현재 SecurityContext에 저장된 인증 정보에서 사용자 ID를 추출합니다.
   */
  public static Long getCurrentUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      throw new CustomException(ErrorCode.UNAUTHORIZED);
    }

    Object principal = authentication.getPrincipal();
    if (principal instanceof UserPrincipal) {
      return ((UserPrincipal) principal).getId();
    }

    throw new CustomException(ErrorCode.UNAUTHORIZED);
  }
}