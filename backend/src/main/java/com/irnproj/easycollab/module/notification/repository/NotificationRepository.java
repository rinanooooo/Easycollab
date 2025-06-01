package com.irnproj.easycollab.module.notification.repository;

import com.irnproj.easycollab.module.notification.entity.Notification;
import com.irnproj.easycollab.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  // 사용자별 알림 전체 조회 (최신순)
  List<Notification> findByUserOrderByCreatedAtDesc(User user);
}