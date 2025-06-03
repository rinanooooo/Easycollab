package com.irnproj.easycollab.module.notification.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String message;

  @Column(name = "is_read", nullable = false)
  private boolean isRead;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  public Notification(String message, boolean read, User user) {
    this.message = message;
    this.isRead = read;
    this.user = user;
  }

  public boolean isRead() {
    return isRead;
  }

  public void markAsRead() {
    this.isRead = true;
  }
}