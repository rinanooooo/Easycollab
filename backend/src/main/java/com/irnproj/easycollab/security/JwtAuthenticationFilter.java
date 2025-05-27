package com.irnproj.easycollab.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * HTTP 요청마다 실행되어 JWT를 검사하고 인증 객체를 생성하는 필터
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  // import 필요: org.slf4j.Logger;
  private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  /**
   * 요청이 들어올 때마다 실행되는 필터 메서드
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    // Authorization 헤더에서 토큰 추출
    String token = parseBearerToken(request);

    try {
      if (token != null) {
        // 토큰에서 사용자 loginId 추출
        String loginId = jwtUtil.extractUsername(token);

        // loginId 기반으로 사용자 정보 조회
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginId);

        // 토큰이 유효하면 인증 객체 생성 후 SecurityContext에 등록
        if (jwtUtil.isTokenValid(token, userDetails)) {
          UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities()
          );
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      }
    } catch (Exception e) {
      // 예외 발생 시 로그 출력 (권장)
      logger.warn("JWT 인증 실패: {}", e.getMessage());
    }

    // 다음 필터로 넘김
    filterChain.doFilter(request, response);
  }

  /**
   * Authorization 헤더에서 Bearer 토큰 파싱
   */
  private String parseBearerToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer ")) {
      return header.substring(7);
    }
    return null;
  }
}