package com.project.configuration.security;

import com.project.configuration.redis.RefreshTokenRepository;
import com.project.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.project.exception.ErrorCode.LOGIN_FAIL;

@RequiredArgsConstructor
public class CustomFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtToken jwtToken;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().contains("/join") || request.getRequestURI().contains("/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = jwtToken.getAccessTokenFromRequest(request);
        String email = jwtToken.getPayloadEmail(accessToken);

        if (accessToken != null && jwtToken.isValidToken(accessToken)) { // Access Token 유효성 검사
            setAuthentication(email);
        } else if (refreshTokenRepository.existByEmail(email)) { // Redis 에 Refresh Token 유효하면 Cookie 에 Access 재발급
            cookieUtil.generateCookie(response, jwtToken.generateAccessToken(email));
            setAuthentication(email);
        }else {
            throw new CustomException(LOGIN_FAIL);
        }

        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(String email) {
        CustomUserDetail userDetail = (CustomUserDetail) customUserDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
    }

    public void setAuthentication(String email) {
        Authentication authentication = getAuthentication(email);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
