package com.project.wallet.service;

import com.project.reference.CheckUserReference;
import com.project.reference.CheckWalletReference;
import com.project.user.entity.User;
import com.project.wallet.entity.Wallet;
import com.project.wallet.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final WalletRepository walletRepository;
    private final CheckWalletReference checkWalletReference;
    private final CheckUserReference checkUserReference;

    public void generateWallet(User user) { // TODO 성능 체크
        Wallet wallet = Wallet.builder()
                .user(user)
                .coinList(new ArrayList<>())
                .balance(10000000)
                .build();

        walletRepository.save(wallet);
        user.setWallet(wallet);
    }

    public double getBalance(HttpServletRequest request) { // 잔액 조회
        String userEmail = checkUserReference.checkUserReference(request);
        User user = checkUserReference.findUserByEmail(userEmail);

        Wallet wallet = checkWalletReference.findWalletByUser(user);

        return wallet.getBalance();
    }

    /** 아직 미완
     *  잔액 재 충전 기준 미정
     */
    public String refillWallet (HttpServletRequest request) {
        String userEmail = checkUserReference.checkUserReference(request);
        User user = checkUserReference.findUserByEmail(userEmail);

        Wallet wallet = checkWalletReference.findWalletByUser(user);

        return null;
    }

}
