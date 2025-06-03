package com.irnproj.easycollab.module.issue.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comment.entity.Comment;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.team.entity.Team;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "issue")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Issue extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status_code_id")
  private ComCode status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "reporter_id", nullable = false)
  private User reporter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assignee_id")
  private User assignee;

  private LocalDate startDate;
  private LocalDate endDate;

  private boolean isPinned;

  @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Builder
  public Issue(String title, String content, ComCode status, Project project, User reporter, User assignee,
               LocalDate startDate, LocalDate endDate, boolean isPinned) {
    this.title = title;
    this.content = content;
    this.status = status;
    this.project = project;
    this.reporter = reporter;
    this.assignee = assignee;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isPinned = isPinned;
  }

  public void update(String title, String content, ComCode status, User assignee,
                     LocalDate startDate, LocalDate endDate, boolean isPinned) {
    this.title = title;
    this.content = content;
    this.status = status;
    this.assignee = assignee;
    this.startDate = startDate;
    this.endDate = endDate;
    this.isPinned = isPinned;
  }

  public String getStatusName() {
    return status != null ? status.getName() : "미정";
  }
}