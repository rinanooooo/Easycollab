package com.irnproj.easycollab.module.issue.entity;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Issue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;

  @Lob
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status_code")
  private ComCode status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id")
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id")
  private User reporter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee_id", nullable = true)
  private User assignee;

  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  public void update(String title, String content, ComCode status, Project project, User reporter, User assignee) {
    this.title = title;
    this.content = content;
    this.status = status;
    this.project = project;
    this.reporter = reporter;
    this.assignee = assignee; // null 허용
    this.updatedAt = LocalDateTime.now(); // 자동 갱신 등
  }

}