package com.irnproj.easycollab.module.code.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "code",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"code_type", "code"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code_type", nullable = false, length = 50)
  private String codeType;

  @Column(nullable = false, length = 50)
  private String code;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(length = 255)
  private String description;

  private LocalDateTime createdAt = LocalDateTime.now();
  private LocalDateTime updatedAt = LocalDateTime.now();

  @PreUpdate
  public void setUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }

  @Builder
  public Code(String codeType, String code, String name, String description) {
    this.codeType = codeType;
    this.code = code;
    this.name = name;
    this.description = description;
  }
}