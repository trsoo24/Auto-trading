package com.project.configuration.security;

import com.project.configuration.redis.RefreshTokenRepository;
import com.project.exception.CustomException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.project.exception.ErrorCode.LOGIN_FAIL;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtToken jwtToken;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CookieUtil cookieUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/user/join") || request.getRequestURI().equals("/user/login")) { // 회원가입 로그인 제외
            log.info("회원가입 & 로그인 API로 필터링 제외");
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getRequestURI().equals("/coin/list") || request.getRequestURI().equals("/coin/fee") || request.getRequestURI().equals("/ticker")) { // 마켓 검색 제외
            log.info("업비트 코인 마켓 조회 API로 필터링 제외");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            log.info("Access Token 조회");
            String accessToken = jwtToken.getAccessTokenFromRequest(request)
                    .filter(jwtToken::isValidToken)
                    .orElse(null);
            String email = jwtToken.getPayloadEmail(accessToken);

            if (accessToken != null) { // Access Token 유효성 검사
                setAuthentication(email);
            } else if (refreshTokenRepository.findById(email).isPresent()) { // Redis 에 Refresh Token 유효하면 Cookie 에 Access 재발급
                log.info("Access Token 만료");
                cookieUtil.generateCookie(response, jwtToken.generateAccessToken(email));
                setAuthentication(email);
            }
        } catch (CustomException e) {
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
