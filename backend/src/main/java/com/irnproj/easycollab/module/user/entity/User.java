package com.irnproj.easycollab.module.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id; // 시스템용 PK

  @Column(nullable = false, unique = true, length = 50)
  private String username; // 로그인 및 계정 ID

  @Column(nullable = false, unique = true, length = 100)
  private String email; // 이메일 전송용

  @Column(nullable = false, unique = true, length = 30)
  private String nickname; // 사용자 표시용 닉네임

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String role;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;
}