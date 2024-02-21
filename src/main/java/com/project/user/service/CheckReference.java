package com.project.user.service;

import com.project.exception.CustomException;
import com.project.user.entity.User;
import com.project.user.entity.dto.SignInDto;
import com.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.project.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CheckReference {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    public void checkDuplicateByEmail(String email) {
        userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(DUPLICATED_EMAIL));
    }

    public void checkDuplicateByNickname(String nickname) {
        userRepository.findByNickname(nickname)
                .orElseThrow(() -> new CustomException(DUPLICATED_NICKNAME));
    }

    public void checkPassword(SignInDto dto) {
        User user = findUserByEmail(dto.getEmail());

        if (!user.getPassword().equals(passwordEncoder.encode(dto.getPassword()))) {
            throw new CustomException(UNMATCHED_PASSWORD);
        }
    }
}
