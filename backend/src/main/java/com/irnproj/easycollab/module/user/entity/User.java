package com.irnproj.easycollab.module.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

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
  private Long id;

  @Column(nullable = false, unique = true, updatable = false, length = 36)
  private String uuid;

  @Column(nullable = false, unique = true, length = 50)
  private String loginId;

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false, unique = true, length = 30)
  private String nickname;

  @Column(nullable = false)
  private String password;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id")
  private Code role;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @PrePersist
  public void assignUuid() {
    this.uuid = UUID.randomUUID().toString();
  }

  @Builder
  public User(String loginId, String username, String nickname, String email, String password, Code role) {
    this.loginId = loginId;
    this.username = username;
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.role = role;
  }
}