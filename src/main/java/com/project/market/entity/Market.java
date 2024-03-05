package com.project.market.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String coinName;
    @Column(nullable = false, unique = true)
    private String coinEngName;
    @Column(nullable = false, unique = true)
    private String market;
    private double buyFee; // 매수 수수료
    private double sellFee; // 매도 수수료

    public void setBuyFee (double val) {
        this.buyFee = val;
    }

    public void setSellFee (double val) {
        this.sellFee = val;
    }
}
