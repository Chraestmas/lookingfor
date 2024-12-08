package com.lookingfor.util;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	private static final String SECRET_KEY = "asdfasdfasdfasdfasdffasdfasdfasdasdfasdfasdfasdfasdfsfad"; // 비밀 키를 사용하여 서명

    // JWT 생성
    public static String generateToken(String username) {
    	try {
            // JWT 토큰 생성
            String token = Jwts.builder()
                    .setSubject(username)  // subject (일반적으로 사용자 이름)
                    .setIssuedAt(new Date())  // 토큰 발급 시간
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // 만료 시간 (1시간 후)
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // HS256 알고리즘과 비밀 키로 서명
                    .compact();  // JWT 토큰 생성

            return token;  // 생성된 토큰을 반환
        } catch (Exception e) {
            e.printStackTrace();  // 예외가 발생하면 로그를 출력
            return null;  // 오류 발생 시 null 반환
        }
    }

    // JWT 검증 및 사용자 이름 추출
    public static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰에서 사용자 이름 추출
    public static String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    // 토큰 유효성 검사
    public static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // 토큰 검증
    public static boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }
}
