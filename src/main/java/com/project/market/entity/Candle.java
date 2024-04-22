package com.project.market.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class Candle {
    private String marketName;
    private String candleDateTime; // 캔들 기준 시간 ( 한국 )
    private double openingPrice; // 시가
    private double highPrice; // 고가
    private double lowPrice; // 저가
    private double tradePrice; // 종가
    private double allTradePrice; // 누적 거래금
    private double allTradeVolume; // 누적 거래량
}