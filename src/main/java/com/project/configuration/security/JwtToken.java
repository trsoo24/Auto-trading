package com.project.configuration.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtToken {
    @Value("${jwt.secret.key}")
    private String jwtKey;
    private final String accessToken = "AccessToken";
    private final String refreshToken = "RefreshToken";
    private final Long accessTokenExpirationPeriod = 1000L * 60 * 60 * 6;
    private final Long refreshTokenExpirationPeriod = 1000L * 60 * 60 * 24 * 2;
    private final String bearer = "Bearer ";
    private final String accessHeader = "Authorization";
    private final String refreshHeader = "Refresh";

    public String generateAccessToken (String email) { // accessToken 생성
        Date date = new Date();
        return Jwts.builder()
                .setSubject(accessToken)
                .setExpiration(new Date(date.getTime() + accessTokenExpirationPeriod))
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }

    /** TODO
     *  Redis 내에 (User Email , RefreshToken) 으로 저장
     *  유효 기간 refreshTokenExpirationPeriod
     */
    public String generateRefreshToken (String email) { // refreshToken 생성
        Date now = new Date();
        Date refreshValidTime = new Date(now.getTime() + refreshTokenExpirationPeriod);

        return Jwts.builder()
                .setSubject(refreshToken)
                .setIssuedAt(now)
                .claim("email", email)
                .setExpiration(refreshValidTime)
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }
}
