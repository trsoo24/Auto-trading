package com.project.market.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MinuteCandleToNowDto {
    private String marketName;
    private int period;
    private int count; // 캔들 개수
}

