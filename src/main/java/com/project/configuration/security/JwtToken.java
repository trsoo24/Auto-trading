package com.project.configuration.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.configuration.redis.RefreshToken;
import com.project.configuration.redis.RefreshTokenRepository;
import com.project.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

import static com.project.exception.ErrorCode.INVALID_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtToken {
    @Value("${jwt.secret.key}")
    private String jwtKey;
    private final String ACCESS_TOKEN = "AccessToken";
    private final String REFRESH_TOKEN = "RefreshToken";
    private final Long ACCESS_TOKEN_EXPIRATION_PERIOD = 1000L * 60 * 60;
    private final Long REFRESH_TOKEN_EXPIRATION_PERIOD = 1000L * 60 * 60 * 24 * 2;
    private final String BEARER = "Bearer ";
    private final String ACCESS_HEADER = "Authorization";
    private final String EMAIL = "email";
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessToken(String email) { // accessToken 생성
        Date date = new Date();

        return JWT.create()
                .withSubject(ACCESS_TOKEN)
                .withExpiresAt(new Date(date.getTime() + ACCESS_TOKEN_EXPIRATION_PERIOD))
                .withClaim(EMAIL, email)
                .sign(Algorithm.HMAC256(jwtKey));
    }

    public void generateRefreshToken(String email) { // refreshToken 생성
        Date now = new Date();
        Date refreshValidTime = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_PERIOD);

        String token = JWT.create()
                .withSubject(REFRESH_TOKEN)
                .withExpiresAt(refreshValidTime)
                .withClaim(EMAIL, email)
                .sign(Algorithm.HMAC256(jwtKey));

        refreshTokenRepository.save(RefreshToken.builder()
                .id(email)
                .refreshToken(token)
                .expiration(REFRESH_TOKEN_EXPIRATION_PERIOD)
                .build());
    }

    public String getPayloadEmail(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(jwtKey))
                    .build()
                    .verify(token)
                    .getClaim(EMAIL)
                    .asString();
        } catch (Exception e) {
            throw new CustomException(INVALID_TOKEN);
        }
    }

    public Optional<String> getAccessTokenFromRequest(HttpServletRequest request) { // Request Header 토큰 추출
        return Optional.ofNullable(request.getHeader(ACCESS_HEADER))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    public boolean isValidToken(String token) {
        try {
            return toDecodedJwtToken(token).getExpiresAt().after(new Date());
        } catch (TokenExpiredException e) {
            return false; // 토큰이 만료된 경우
        } catch (Exception e) {
            return false; // 다른 예외도 우선 false 처리
        }
    }

    public DecodedJWT toDecodedJwtToken(String encodedToken) {
        return JWT.require(Algorithm.HMAC256(jwtKey)).build().verify(encodedToken);
    }

}
