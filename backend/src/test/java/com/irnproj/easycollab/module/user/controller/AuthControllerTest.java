package com.irnproj.easycollab.module.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.irnproj.easycollab.module.user.dto.LoginRequestDto;
import com.irnproj.easycollab.module.user.dto.SignupRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

  @LocalServerPort
  int port;

  @Autowired
  ObjectMapper objectMapper;

  RestTemplate rest = new RestTemplate();

  String baseUrl() {
    return "http://localhost:" + port + "/api/auth";
  }
}