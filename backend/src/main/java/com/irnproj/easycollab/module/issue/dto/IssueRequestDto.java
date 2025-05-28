package com.irnproj.easycollab.module.issue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class IssueRequestDto {
  @NotBlank
  private String title;
  private String content;
  @NotBlank
  private String statusCode; // ex) "OPEN", "CLOSED"
  @NotNull
  private Long projectId;
  @NotNull
  private Long reporterId;
}