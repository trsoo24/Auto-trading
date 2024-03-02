package com.project.market.entity;

import com.project.market.entity.type.Change;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticker {
    private String marketName; // 마켓 코드
    private LocalDateTime lastTradeTimeStamp; // 최근 거래 시각 (yyyy-MM-dd HH:mm:ss)
    private double openPrice; // 시가
    private double highPrice; // 고가
    private double lowPrice; // 저가
    private double tradePrice; // 현재가
    private String changed; // EVEN 보합, RISE 상승, FALL 하락
    private double changedPrice; // 변화액
    private double changedRate; // 변화율
    private double allDayTradePrice; // 24시간 누적 거래대금
    private double allDayTradeVolume; // 24시간 누적 거래량
    private double highestWeekPrice; // 52주 신고가
    private LocalDate highestDay; // 52주 신고가 달성일 (yyyy-MM-dd)
    private double lowestWeekPrice; // 52주 신저가
    private LocalDate lowestDay; // 52주 신저가 달성일 (yyyy-MM-dd)
}
