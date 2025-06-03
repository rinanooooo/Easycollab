package com.irnproj.easycollab.module.notification.controller;

import com.irnproj.easycollab.module.notification.dto.NotificationResponseDto;
import com.irnproj.easycollab.module.notification.service.NotificationService;
import com.irnproj.easycollab.security.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Notification", description = "알림 관련 API")
@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping
  @Operation(summary = "알림 목록 조회", description = "현재 로그인한 사용자의 알림 목록을 조회합니다.")
  public List<NotificationResponseDto> getNotifications() {
    return notificationService.getNotificationsForCurrentUser();
  }

  @PostMapping("/{notificationId}/read")
  @Operation(summary = "알림 읽음 처리", description = "특정 알림을 읽음 처리합니다.")
  public void markAsRead(@PathVariable Long notificationId) {
    notificationService.markAsRead(notificationId);
  }

  @PostMapping("/read-all")
  @Operation(summary = "전체 알림 읽음 처리", description = "모든 알림을 읽음 상태로 처리합니다.")
  public void markAllAsRead() {
    notificationService.markAllAsRead();
  }
}