package com.irnproj.easycollab.module.notification.service;

import com.irnproj.easycollab.common.exception.CustomException;
import com.irnproj.easycollab.common.exception.ErrorCode;
import com.irnproj.easycollab.module.notification.dto.NotificationResponseDto;
import com.irnproj.easycollab.module.notification.entity.Notification;
import com.irnproj.easycollab.module.notification.repository.NotificationRepository;
import com.irnproj.easycollab.module.user.entity.User;
import com.irnproj.easycollab.module.user.repository.UserRepository;
import com.irnproj.easycollab.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

  private final NotificationRepository notificationRepository;
  private final UserRepository userRepository;

  /**
   * 특정 사용자에게 알림을 생성합니다.
   */
  @Transactional
  public void createNotification(Long userId, String message) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    Notification notification = Notification.builder()
        .user(user)
        .message(message)
        .read(false)
        .build();

    notificationRepository.save(notification);
  }

  /**
   * 현재 로그인한 사용자의 알림 목록을 조회합니다.
   * 최신순 정렬 및 읽음 여부 포함
   */
  public List<NotificationResponseDto> getNotificationsForCurrentUser() {
    Long userId = SecurityUtil.getCurrentUserId();

    return notificationRepository.findByUserId(userId).stream()
        .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
        .map(NotificationResponseDto::of)
        .toList();
  }

  /**
   * 특정 알림을 읽음 처리합니다.
   * 본인의 알림이 아닌 경우 예외 발생
   */
  @Transactional
  public void markAsRead(Long notificationId) {
    Notification notification = notificationRepository.findById(notificationId)
        .orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND));

    Long currentUserId = SecurityUtil.getCurrentUserId();
    if (!notification.getUser().getId().equals(currentUserId)) {
      throw new CustomException(ErrorCode.UNAUTHORIZED_NOTIFICATION_ACCESS);
    }

    notification.markAsRead();
  }

  /**
   * 사용자의 모든 알림을 읽음 처리합니다.
   */
  @Transactional
  public void markAllAsRead() {
    Long currentUserId = SecurityUtil.getCurrentUserId();
    List<Notification> notifications = notificationRepository.findByUserId(currentUserId);

    notifications.forEach(Notification::markAsRead);
  }
}