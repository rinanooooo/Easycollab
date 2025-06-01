package com.irnproj.easycollab.module.notification.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 알림 내용 (ex. "프로젝트A의 새로운 이슈가 생겼습니다.")
  @Column(nullable = false, length = 255)
  private String content;

  // 알림 대상 사용자
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  // 관련 URL (클릭 시 이동할 경로, ex. /projects/1/issues/2)
  @Column(length = 255)
  private String url;

  // 읽음 여부
  private boolean isRead;

  // 읽음 상태 설정 메서드
  public void setRead(boolean isRead) {
    this.isRead = isRead;
  }
}