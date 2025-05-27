package com.irnproj.easycollab.module.comCode.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "code",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"code_type", "code"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComCode extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code_type", nullable = false, length = 50)
  private String codeType;

  @Column(nullable = false, length = 50)
  private String code; // ex: "ROLE_USER", "ROLE_ADMIN"

  @Column(nullable = false, length = 100)
  private String name; // ex: "일반 사용자", "관리자"

  @Column(length = 255)
  private String description;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  @PreUpdate
  public void setUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }

  @Builder
  public ComCode(String codeType, String code, String name, String description) {
    this.codeType = codeType;
    this.code = code;
    this.name = name;
    this.description = description;
  }
}