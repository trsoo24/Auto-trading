package com.project.user.service;

import com.project.configuration.security.JwtToken;
import com.project.exception.CustomException;
import com.project.exception.ErrorCode;
import com.project.user.entity.User;
import com.project.user.entity.dto.SignInDto;
import com.project.user.entity.dto.SignUpDto;
import com.project.user.entity.type.Role;
import com.project.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CheckReference checkReference;
    private final PasswordEncoder passwordEncoder;
    private final JwtToken jwtToken;

    public String signUp (SignUpDto signUpDto) {
        // 이메일 , 닉네임 중복 검사
        checkReference.checkDuplicateByEmail(signUpDto.getEmail());
        checkReference.checkDuplicateByNickname(signUpDto.getNickname());

        User user = User.builder()
                .email(signUpDto.getEmail())
                .password(passwordEncoder.encode(signUpDto.getPassword()))
                .nickname(signUpDto.getNickname())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return user.getEmail() + "님의 회원가입 완료";
    }

    public String signIn (SignInDto signInDto) {
        User user = checkReference.findUserByEmail(signInDto.getEmail());
        // 비밀번호 일치여부 체크
        checkReference.checkPassword(user, signInDto.getPassword());

        /** TODO
         *  refresh Token redis 저장
         */

        return jwtToken.generateAccessToken(signInDto.getEmail());
    }

}