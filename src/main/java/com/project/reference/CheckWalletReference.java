package com.project.reference;

import com.project.exception.CustomException;
import com.project.user.entity.User;
import com.project.wallet.entity.Wallet;
import com.project.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.exception.ErrorCode.INVALID_WALLET;

@Service
@RequiredArgsConstructor
public class CheckWalletReference {
    private final WalletRepository walletRepository;

    public Wallet findWalletByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(INVALID_WALLET));
    }
}
