package com.irnproj.easycollab.module.comCode.repository;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComCodeRepository extends JpaRepository<ComCode, Long> {
  List<ComCode> findByCodeType(String codeType);
  Optional<ComCode> findByCodeTypeAndCode(String codeType, String code);
  boolean existsByCodeTypeAndCode(String codeType, String code);
}

