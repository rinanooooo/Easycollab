package com.irnproj.easycollab.module.comCode.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "com_code", uniqueConstraints = {@UniqueConstraint(columnNames = {"code_type", "code"})})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ComCode extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "code_type", nullable = false, length = 50)
  private String codeType; // 코드 그룹명 (ex: ISSUE_STATUS, ROLE)

  @Column(nullable = false, length = 50)
  private String code;     // 실제 코드 (ex: PLANNED, MANAGER)

  @Column(nullable = false, length = 100)
  private String name;     // 코드 이름 (ex: 예정, 부장)

  @Column(length = 255)
  private String description;

  // @CreatedDate, @LastModifiedDate → BaseTimeEntity에 포함
}