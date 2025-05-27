package com.irnproj.easycollab.module.code.controller;

import com.irnproj.easycollab.module.code.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class CodeController {

  private final CodeService codeService;

  @GetMapping("/{type}")
  public ResponseEntity<List<Code>> getCodes(@PathVariable("type") String codeType) {
    return ResponseEntity.ok(codeService.getCodesByType(codeType));
  }
}