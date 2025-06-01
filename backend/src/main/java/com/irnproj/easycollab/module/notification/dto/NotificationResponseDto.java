package com.irnproj.easycollab.module.notification.dto;

import com.irnproj.easycollab.module.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class NotificationResponseDto {

  private Long id;                // 알림 ID
  private String content;         // 알림 내용
  private String url;             // 이동할 경로
  private boolean isRead;         // 읽음 여부
  private LocalDateTime createdAt; // 생성일

  public static NotificationResponseDto fromEntity(Notification notification) {
    return NotificationResponseDto.builder()
        .id(notification.getId())
        .content(notification.getContent())
        .url(notification.getUrl())
        .isRead(notification.isRead())
        .createdAt(notification.getCreatedAt())
        .build();
  }
}