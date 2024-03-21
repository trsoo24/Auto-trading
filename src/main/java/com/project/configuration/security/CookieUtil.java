package com.project.configuration.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieUtil {
    private static final String COOKIE_NAME = "INVEST_COOKIE";
    private static final int MAX_AGE = 600;
    public void generateCookie(HttpServletResponse response, String accessToken) {
        Cookie cookie = new Cookie(COOKIE_NAME, accessToken);
        cookie.setPath("/");
        cookie.setMaxAge(MAX_AGE);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }

    public String getCookie(HttpServletRequest request) { // cookie 에 유저 값 없을 시 null
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void deleteCookie(HttpServletRequest request) { // 유효기간 즉시 만료시키기
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(COOKIE_NAME)) {
                    cookie.setMaxAge(0);
                }
            }
        }
    }

}
