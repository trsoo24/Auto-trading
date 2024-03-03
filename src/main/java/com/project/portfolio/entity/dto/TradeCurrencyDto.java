package com.project.portfolio.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TradeCurrencyDto {
    private String marketName; // KRW-BTC, 비트코인 둘 다 가능
    private double volume; // 희망 구매 개수
}
