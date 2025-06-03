package com.irnproj.easycollab.module.project.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectMemberRequestDto {
  private Long userId;
  private Long roleCodeId;
}

