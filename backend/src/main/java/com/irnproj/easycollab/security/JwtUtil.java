package com.irnproj.easycollab.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * JWT 생성 및 검증을 담당하는 유틸 클래스
 */
@Component
public class JwtUtil {

  private final Key secretKey;          // 서명에 사용될 비밀 키
  private final long expirationMillis;  // 토큰 만료 시간 (ms 단위)

  public JwtUtil(@Value("${jwt.secret}") String secret,
                 @Value("${jwt.expiration}") long expirationMillis) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes()); // 시크릿 키를 기반으로 HMAC 키 생성
    this.expirationMillis = expirationMillis;
  }

  /**
   * JWT 토큰 생성 - loginId를 subject에 저장
   */
  public String generateToken(String loginId) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + expirationMillis);

    return Jwts.builder()
        .setSubject(loginId)              // 토큰의 주제 (사용자 식별자)
        .setIssuedAt(now)                 // 발급 시각
        .setExpiration(expiryDate)       // 만료 시각
        .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 알고리즘 설정
        .compact();
  }

  /**
   * 토큰에서 사용자 아이디 (loginId) 추출
   */
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * 토큰에서 특정 Claim 추출 (일반화된 메서드)
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * 토큰의 모든 Claim 추출
   */
  private Claims extractAllClaims(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (JwtException | IllegalArgumentException e) {
      throw new IllegalArgumentException("유효하지 않은 토큰입니다.", e);
    }
  }

  /**
   * 토큰 유효성 검사: 사용자 일치 + 만료 여부 확인
   */
  public boolean isTokenValid(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  /**
   * 토큰이 만료되었는지 여부
   */
  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  /**
   * 토큰 만료 시각 추출
   */
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }
}