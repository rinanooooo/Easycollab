package com.irnproj.easycollab.module.comCode.service;

import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import lombok.RequiredArgsConstructor;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComCodeService {

  private final ComCodeRepository comCodeRepository;

  public List<ComCode> getCodesByType(String codeType) {
    return comCodeRepository.findByCodeType(codeType);
  }

  public ComCode getCode(String codeType, String code) {
    return comCodeRepository.findByCodeTypeAndCode(codeType, code)
        .orElseThrow(() -> new IllegalArgumentException("코드를 찾을 수 없습니다."));
  }
}