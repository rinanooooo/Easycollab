package com.irnproj.easycollab.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

  private final Key secretKey;
  private final long expirationMillis;

  public JwtUtil(
      @Value("${jwt.secret}") String secret,
      @Value("${jwt.expiration}") long expirationMillis
  ) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    this.expirationMillis = expirationMillis;
  }

  // JWT 토큰 생성
  public String generateToken(String username) {
    Date now = new Date();
    Date expiry = new Date(now.getTime() + expirationMillis);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(expiry)
        .signWith(secretKey, SignatureAlgorithm.HS256)
        .compact();
  }

  // 토큰에서 사용자 이름 추출
  public String extractUsername(String token) {
    return parseClaims(token).getSubject();
  }

  // 토큰 유효성 검사
  public boolean validateToken(String token) {
    try {
      parseClaims(token);
      return true;
    } catch (JwtException | IllegalArgumentException e) {
      return false;
    }
  }

  // Claims 파싱
  private Claims parseClaims(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody();
  }
}