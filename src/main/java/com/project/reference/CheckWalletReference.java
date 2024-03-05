package com.project.reference;

import com.project.exception.CustomException;
import com.project.user.entity.User;
import com.project.wallet.entity.Wallet;
import com.project.wallet.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.exception.ErrorCode.INVALID_WALLET;

@Service
@RequiredArgsConstructor
public class CheckWalletReference {
    private final WalletRepository walletRepository;
    private final CheckUserReference checkUserReference;

    public Wallet findWalletByUser(User user) {
        return walletRepository.findByUser(user)
                .orElseThrow(() -> new CustomException(INVALID_WALLET));
    }

    public Wallet findWalletByRequestHeader(HttpServletRequest request) {
        String email = checkUserReference.checkUserReference(request);

        User user = checkUserReference.findUserByEmail(email);

        return findWalletByUser(user);
    }
}
