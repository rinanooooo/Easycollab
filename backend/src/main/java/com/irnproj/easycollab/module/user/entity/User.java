package com.irnproj.easycollab.module.user.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comment.entity.Comment;
import com.irnproj.easycollab.module.issue.entity.Issue;
import com.irnproj.easycollab.module.notification.entity.Notification;
import com.irnproj.easycollab.module.project.entity.ProjectMember;
import com.irnproj.easycollab.module.team.entity.TeamMember;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 100)
  private String loginId;

  @Column(nullable = false, length = 100)
  private String username;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

  @Column(nullable = false, length = 100)
  private String password;

  @Column(nullable = false, length = 50)
  private String nickname;

  @Column(name = "profile_image_url", length = 255)
  private String profileImageUrl;

  @Column(nullable = false, unique = true, updatable = false)
  private String uuid;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_code_id")
  private ComCode role;
// → role.getCodeType() == "ROLE"

  @OneToMany(mappedBy = "user")
  private List<TeamMember> teamMembers = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<ProjectMember> projectMembers = new ArrayList<>();

  @OneToMany(mappedBy = "assignee")
  private List<Issue> issues = new ArrayList<>();

  @OneToMany(mappedBy = "writer")
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Notification> notifications = new ArrayList<>();

  // @CreatedDate, @LastModifiedDate → BaseTimeEntity에 포함

  @PrePersist
  public void assignUuid() {
    this.uuid = UUID.randomUUID().toString();
  }

  @Builder
  public User(String loginId, String username, String nickname, String email, String password, ComCode role) {
    this.loginId = loginId;
    this.username = username;
    this.nickname = nickname;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public String getRoleName() {
    return role != null ? role.getName() : "일반 사용자";
  }

  public String getJoinedDateFormatted() {
    return getCreatedAt() != null ? getCreatedAt().toLocalDate().toString() : "";
  }
}