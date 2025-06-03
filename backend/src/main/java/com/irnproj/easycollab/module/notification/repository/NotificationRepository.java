package com.irnproj.easycollab.module.notification.repository;

import com.irnproj.easycollab.module.notification.entity.Notification;
import com.irnproj.easycollab.module.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findByUserId(Long userId);
}