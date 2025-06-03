package com.irnproj.easycollab.module.project.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"project", "user"})
@Table(
    name = "project_member",
    uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "user_id"})
)
public class ProjectMember extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "project_id", nullable = false)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_code_id")
  private ComCode roleCode;

  public String getRoleName() {
    return roleCode != null ? roleCode.getName() : "참여자";
  }

  public void assignRole(ComCode newRole) {
    this.roleCode = newRole;
  }

  @Builder
  public ProjectMember(Project project, User user, ComCode roleCode) {
    this.project = project;
    this.user = user;
    this.roleCode = roleCode;
  }

  public void setProject(Project project) {
    this.project = project;
    project.getProjectMembers().add(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ProjectMember)) return false;
    return id != null && id.equals(((ProjectMember) o).id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  public void updateRole(ComCode newRole) {
    this.roleCode = newRole;
  }
}