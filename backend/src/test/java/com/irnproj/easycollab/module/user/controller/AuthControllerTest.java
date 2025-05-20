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

  @Test
  void 회원가입_로그인_인증_전체흐름_테스트() throws Exception {
    // 회원가입
    SignupRequestDto signup = new SignupRequestDto("testuser", "tester", "test@example.com", "password");
    ResponseEntity<String> signupRes = rest.postForEntity(
        baseUrl() + "/signup",
        signup,
        String.class
    );
    assertThat(signupRes.getStatusCode()).isEqualTo(HttpStatus.OK);

    // 로그인
    LoginRequestDto login = new LoginRequestDto("testuser", "password");
    ResponseEntity<String> loginRes = rest.postForEntity(
        baseUrl() + "/login",
        login,
        String.class
    );
    assertThat(loginRes.getStatusCode()).isEqualTo(HttpStatus.OK);
    String token = objectMapper.readTree(loginRes.getBody()).get("token").asText();

    // 인증 요청
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + token);
    HttpEntity<Void> request = new HttpEntity<>(headers);

    ResponseEntity<String> userInfoRes = rest.exchange(
        "http://localhost:" + port + "/api/user/me",
        HttpMethod.GET,
        request,
        String.class
    );
    assertThat(userInfoRes.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(userInfoRes.getBody()).contains("testuser");
  }
}