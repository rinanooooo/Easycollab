package com.irnproj.easycollab.module.code.repository;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
  List<Code> findByCodeType(String codeType);
  Optional<Code> findByCodeTypeAndCode(String codeType, String code);
}

