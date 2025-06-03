package com.irnproj.easycollab.module.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectRequestDto {

  @NotNull
  private Long teamId;

  @NotNull
  private Long ownerId;

  @NotBlank
  @JsonProperty("projectName")
  private String name;

  private String description;

  @NotNull
  private ComCode status;
}