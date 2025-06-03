package com.irnproj.easycollab.module.team.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "team_member", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"team_id", "user_id"})
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"team", "user"})
public class TeamMember extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", nullable = false)
  private Team team;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_code_id")
  private ComCode role; // codeType = "ROLE"

  public String getRoleName() {
    return role != null ? role.getName() : "팀원";
  }

  @Builder
  public TeamMember(Team team, User user, ComCode role) {
    this.team = team;
    this.user = user;
    this.role = role;
  }
}