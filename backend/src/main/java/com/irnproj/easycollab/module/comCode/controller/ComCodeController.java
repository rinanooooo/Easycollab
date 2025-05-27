package com.irnproj.easycollab.module.comCode.controller;

import com.irnproj.easycollab.module.comCode.entity.ComCode;
import com.irnproj.easycollab.module.comCode.service.ComCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/codes")
@RequiredArgsConstructor
public class ComCodeController {

  private final ComCodeService comCodeService;

  @GetMapping("/{type}")
  public ResponseEntity<List<ComCode>> getCodes(@PathVariable("type") String codeType) {
    return ResponseEntity.ok(comCodeService.getCodesByType(codeType));
  }
}