package com.project.wallet.service;

import com.project.user.entity.User;
import com.project.wallet.entity.Wallet;
import com.project.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;

    public void generateWallet(User user) { // TODO 성능 체크
        Wallet wallet = Wallet.builder()
                .user(user)
                .coinList(new ArrayList<>())
                .balance(10000000)
                .build();

        walletRepository.save(wallet);
        user.setWallet(wallet);
    }

    public Wallet refillWallet () {
        /** 다시 잔액을 채워주는 기준 필요
         *  1. 파산
         *  2. 월마다
          */
        return null;
    }
}
