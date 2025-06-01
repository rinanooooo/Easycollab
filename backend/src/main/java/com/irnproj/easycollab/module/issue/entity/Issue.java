package com.irnproj.easycollab.module.issue.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Issue extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 200)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  // 이슈 시작일 (예정일 또는 실제 작업 시작일)
  @Column
  private LocalDate startDate;

  // 이슈 마감일 (목표 종료일)
  @Column
  private LocalDate endDate;

  // 상태 코드 (예: "PLANNED", "IN_PROGRESS", "COMPLETED")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status_code", nullable = false)
  private ComCode status;

  // 작성자
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id", nullable = false)
  private User reporter;

  // 담당자 (nullable)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee_id")
  private User assignee;

  // 소속 프로젝트
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  public void update(String title, String content, Project project, ComCode status,
                     User assignee, LocalDate startDate, LocalDate endDate) {
    this.title = title;
    this.content = content;
    this.project = project;
    this.status = status;
    this.assignee = assignee;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
