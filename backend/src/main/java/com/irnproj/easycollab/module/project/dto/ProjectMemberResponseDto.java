package com.irnproj.easycollab.module.project.dto;

import com.irnproj.easycollab.module.project.entity.ProjectMember;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMemberResponseDto {
  private Long id;
  private Long userId;
  private String userNickname;
  private String roleName;

  public static ProjectMemberResponseDto from(ProjectMember member) {
    return ProjectMemberResponseDto.builder()
        .id(member.getId())
        .userId(member.getUser().getId())
        .userNickname(member.getUser().getNickname())
        .roleName(member.getRoleCode().getName())
        .build();
  }
}
