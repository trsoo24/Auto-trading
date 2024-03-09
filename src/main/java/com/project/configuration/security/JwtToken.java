package com.project.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtToken {
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.secret.key}")
    private String jwtKey;
    private final String ACCESS_TOKEN = "AccessToken";
    private final String REFRESH_TOKEN = "RefreshToken";
    private final Long ACCESS_TOKEN_EXPIRATION_PERIOD = 1000L * 60 * 60 * 6;
    private final Long REFRESH_TOKEN_EXPIRATION_PERIOD = 1000L * 60 * 60 * 24 * 2;
    private final String BEARER = "Bearer ";
    private final String ACCESS_HEADER = "Authorization";
    private final String REFRESH_HEADER = "Refresh";

    public String generateAccessToken (String email) { // accessToken 생성
        Date date = new Date();
        return Jwts.builder()
                .setSubject(ACCESS_TOKEN)
                .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_EXPIRATION_PERIOD))
                .claim("email", email)
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }

    public void generateRefreshToken (String email) { // refreshToken 생성
        Date now = new Date();
        Date refreshValidTime = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_PERIOD);

        String token = Jwts.builder()
                        .setSubject(REFRESH_TOKEN)
                        .setIssuedAt(now)
                        .claim("email", email)
                        .setExpiration(refreshValidTime)
                        .signWith(SignatureAlgorithm.HS256, jwtKey)
                        .compact();

        redisTemplate.opsForValue().set(email, token);
    }

    public String getPayloadSub(String token) { // payload 에 sub : email 값 추출
        return Jwts.parser()
                .setSigningKey(jwtKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getAccessTokenFromRequest(HttpServletRequest request) { // Request Header 토큰 추출
        String tokenWithBearer = request.getHeader(ACCESS_HEADER);

        if (tokenWithBearer.startsWith(BEARER)) {
            return tokenWithBearer.substring(BEARER.length());
        }
        return null;
    }

    public boolean isValidToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);

        return claims.getBody().getExpiration().before(new Date());
    }
}
