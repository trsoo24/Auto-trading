package com.project.portfolio.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Quote { // 포트폴리오 현 시세 조회 DTO
    private String marketName;
    private double quote; // 현 거래가
    private double averageValue; // 매입 평단가
    private double volume; // 가상 화폐 개수
    private double profitLossAmount; // 투자 손익금
    private double profitLossPercent; // 투자 손익률
    private LocalDateTime lastTradeTimeStamp;
}
