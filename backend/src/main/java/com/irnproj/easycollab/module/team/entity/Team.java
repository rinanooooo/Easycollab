package com.irnproj.easycollab.module.team.entity;

import com.irnproj.easycollab.module.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(length = 500)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by")
  private User createdBy;

  private LocalDateTime createdAt = LocalDateTime.now();

  public Team(String name, String description, User createdBy) {
    this.name = name;
    this.description = description;
    this.createdBy = createdBy;
  }
}