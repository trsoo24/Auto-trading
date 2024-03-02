package com.project.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // USER
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    DUPLICATED_NICKNAME(HttpStatus.BAD_REQUEST, "중복된 닉네임입니다."),
    UNMATCHED_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // WebSocket
    WEBSOCKET_CLOSED(HttpStatus.BAD_REQUEST, "서버가 닫혀있습니다."),


    // Market
    MARKET_NOT_FOUND(HttpStatus.NOT_FOUND, "가상 화폐를 찾을 수 없습니다."),
    INCORRECT_SEARCH_METHOD(HttpStatus.BAD_REQUEST, "올바르지 않은 검색 방법입니다.")
    ;
    private final HttpStatus httpStatus;
    private final String detail;
}
