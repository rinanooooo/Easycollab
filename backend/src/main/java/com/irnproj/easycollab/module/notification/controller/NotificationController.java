package com.irnproj.easycollab.module.notification.controller;

import com.irnproj.easycollab.module.notification.dto.NotificationResponseDto;
import com.irnproj.easycollab.module.notification.service.NotificationService;
import com.irnproj.easycollab.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  /**
   * 내 알림 전체 조회 (최신순)
   */
  @GetMapping("/me")
  public ResponseEntity<List<NotificationResponseDto>> getMyNotifications(
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    List<NotificationResponseDto> notifications = notificationService.getMyNotifications(userPrincipal.getId());
    return ResponseEntity.ok(notifications);
  }

  /**
   * 알림 읽음 처리
   */
  @PatchMapping("/{id}/read")
  public ResponseEntity<Void> markAsRead(
      @PathVariable Long id,
      @AuthenticationPrincipal UserPrincipal userPrincipal
  ) {
    notificationService.markAsRead(id, userPrincipal.getId());
    return ResponseEntity.ok().build();
  }
}