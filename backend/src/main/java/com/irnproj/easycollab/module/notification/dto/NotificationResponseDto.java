package com.irnproj.easycollab.module.notification.dto;

import com.irnproj.easycollab.module.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NotificationResponseDto {

  private Long id;
  private String message;
  private boolean isRead;
  private LocalDateTime createdAt;

  public static NotificationResponseDto of(Notification notification) {
    NotificationResponseDto dto = new NotificationResponseDto();
    dto.setId(notification.getId());
    dto.setMessage(notification.getMessage());
    dto.setRead(notification.isRead());  // getter는 여전히 isRead()
    dto.setCreatedAt(notification.getCreatedAt());
    return dto;
  }
}
