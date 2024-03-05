package com.project.configuration.security;

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
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtToken jwtToken;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtToken.getAccessTokenFromRequest(request);

        if (accessToken != null && jwtToken.isValidToken(accessToken)) {
            String email = jwtToken.getPayloadSub(accessToken);
            Authentication authentication = getAuthentication(email);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else {
            throw new CustomException(LOGIN_FAIL);
        }

        filterChain.doFilter(request, response);
    }

    public Authentication getAuthentication(String email) {
        UserDetailImpl userDetail = (UserDetailImpl) userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetail, "", userDetail.getAuthorities());
    }
}
