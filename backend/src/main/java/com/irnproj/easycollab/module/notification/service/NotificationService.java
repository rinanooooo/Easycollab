package com.irnproj.easycollab.module.notification.service;

import com.irnproj.easycollab.module.notification.dto.NotificationResponseDto;
import com.irnproj.easycollab.module.notification.entity.Notification;
import com.irnproj.easycollab.module.notification.repository.NotificationRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;

  /**
   * 내 알림 전체 조회 (최신순)
   */
  @Transactional(readOnly = true)
  public List<NotificationResponseDto> getMyNotifications(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

    return notificationRepository.findByUserOrderByCreatedAtDesc(user).stream()
        .map(NotificationResponseDto::fromEntity)
        .collect(Collectors.toList());
  }

  /**
   * 새 알림 저장
   */
  @Transactional
  public void createNotification(Long userId, String content, String url) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

    Notification notification = Notification.builder()
        .user(user)
        .content(content)
        .url(url)
        .isRead(false)
        .build();

    notificationRepository.save(notification);
  }

  /**
   * 알림 읽음 처리
   */
  @Transactional
  public void markAsRead(Long notificationId, Long userId) {
    Notification notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new IllegalArgumentException("알림을 찾을 수 없습니다."));

    if (!notification.getUser().getId().equals(userId)) {
      throw new IllegalArgumentException("본인의 알림만 읽음 처리할 수 있습니다.");
    }

    notification.setRead(true);
  }
}