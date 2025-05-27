package com.irnproj.easycollab.module.code.service;

import com.irnproj.easycollab.module.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeService {

  private final CodeRepository codeRepository;

  public List<Code> getCodesByType(String codeType) {
    return codeRepository.findByCodeType(codeType);
  }

  public Code getCode(String codeType, String code) {
    return codeRepository.findByCodeTypeAndCode(codeType, code)
        .orElseThrow(() -> new IllegalArgumentException("코드를 찾을 수 없습니다."));
  }
}