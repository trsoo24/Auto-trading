package com.project.wallet.entity;

import com.project.portfolio.entity.Portfolio;
import com.project.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "wallet")
    private List<Portfolio> coinList;
    @OneToOne(mappedBy = "wallet")
    private User user;
    private double balance; // 잔액 현금

    public void useBalance(double cost) {
        this.balance -= cost;
    }

    public void plusBalance(double money) {
        this.balance += money;
    }
}
