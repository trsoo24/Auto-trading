package com.project.reference;

import com.project.configuration.security.JwtToken;
import com.project.exception.CustomException;
import com.project.user.entity.User;
import com.project.user.repository.UserRepository;
import com.project.wallet.entity.Wallet;
import com.project.wallet.repository.WalletRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CheckUserReference {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtToken jwtToken;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    public void checkDuplicateByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(DUPLICATED_EMAIL);
        }
    }

    public void checkDuplicateByNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new CustomException(DUPLICATED_NICKNAME);
        }
    }

    public void checkPassword(User user, String password) {
        if (!user.getPassword().equals(passwordEncoder.encode(password))) {
            throw new CustomException(UNMATCHED_PASSWORD);
        }
    }

    public String checkUserReference(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return jwtToken.getPayloadSub(token);
    }
}
