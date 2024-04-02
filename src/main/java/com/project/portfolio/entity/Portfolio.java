package com.project.portfolio.entity;

import com.project.user.entity.User;
import com.project.wallet.entity.Wallet;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
    @Column(nullable = false)
    private String marketName;
    private double price; // 누적 매수액
    private double averageValue; // 매수 평균가
    private double volume; // 개수
    @LastModifiedDate
    private LocalDateTime lastTradeTimeStamp; // 마지막 거래 시각

    public void patchPrice (double value) {
        this.price = value;
    }
    public void patchVolume (double value) {
        this.volume = value;
    }
    public void patchAverageValue (double price, double volume) {
        this.averageValue = price / volume;
    }
}
