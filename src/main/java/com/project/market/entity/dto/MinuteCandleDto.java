package com.project.market.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MinuteCandleDto {
    private String marketName;
    private int period;
    private String toDate; // 마지막 캔들 시각
    private int count; // 캔들 개수
}
