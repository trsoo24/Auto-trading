package com.project.market.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CandleToNowDto {
    private String marketName;
    private String period;
    private int count; // 캔들 개수
}
