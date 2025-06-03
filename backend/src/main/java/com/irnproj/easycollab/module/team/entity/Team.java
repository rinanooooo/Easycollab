package com.irnproj.easycollab.module.team.entity;

import com.irnproj.easycollab.common.entity.BaseTimeEntity;
import com.irnproj.easycollab.module.project.entity.Project;
import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "team")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Team extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String teamName;

  @Column(length = 255, nullable = true)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "owner_id")
  private User owner; // 팀 생성자

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<TeamMember> teamMembers = new ArrayList<>();

  @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Project> projects = new ArrayList<>();

  public void update(String teamName, String description) {
    this.teamName = teamName;
    this.description = (description != null) ? description : this.description;
  }

}