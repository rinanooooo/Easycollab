package com.irnproj.easycollab.module.comCode.service;

import com.irnproj.easycollab.module.comCode.dto.ComCodeResponseDto;
import com.irnproj.easycollab.module.comCode.exception.ComCodeNotFoundException;
import com.irnproj.easycollab.module.comCode.repository.ComCodeRepository;
import lombok.RequiredArgsConstructor;
import com.irnproj.easycollab.module.comCode.entity.ComCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComCodeService {

  private final ComCodeRepository comCodeRepository;

  public List<ComCode> getCodesByType(String codeType) {
    return comCodeRepository.findByCodeType(codeType);
  }

  @Transactional(readOnly = true)
  public List<ComCodeResponseDto> getComCodesByType(String codeType) {
    List<ComCode> codeList = comCodeRepository.findByCodeType(codeType);

    return codeList.stream()
        .map(ComCodeResponseDto::new)
        .toList();
  }

  @Transactional(readOnly = true)
  public ComCodeResponseDto getComCode(String codeType, String code) {
    ComCode comCode = comCodeRepository.findByCodeTypeAndCode(codeType, code)
        .orElseThrow(() -> new ComCodeNotFoundException(
            String.format("[%s:%s] 코드가 존재하지 않습니다.", codeType, code))
        );
    return new ComCodeResponseDto(comCode);
  }

}