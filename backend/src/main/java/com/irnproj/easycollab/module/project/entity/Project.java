package com.irnproj.easycollab.module.project.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.issue.entity.Issue;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(length = 255)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private User owner;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status_code_id")
  private ComCode status;

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<ProjectMember> projectMembers = new ArrayList<>();

  @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Issue> issues = new ArrayList<>();

  @Builder
  public Project(String name, String description, Team team, ComCode status) {
    this.name = name;
    this.description = description;
    this.team = team;
    this.status = status;
  }

  public void update(String name, String description, ComCode status) {
    this.name = name;
    this.description = description;
    this.status = status;
  }
}