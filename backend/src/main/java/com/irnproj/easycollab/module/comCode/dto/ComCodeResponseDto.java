package com.irnproj.easycollab.module.comCode.dto;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import lombok.Getter;

@Getter
public class ComCodeResponseDto {

  private final String codeType;
  private final String code;
  private final String name;
  private final String description;

  public ComCodeResponseDto(ComCode comCode) {
    this.codeType = comCode.getCodeType();
    this.code = comCode.getCode();
    this.name = comCode.getName();
    this.description = comCode.getDescription();
  }
}