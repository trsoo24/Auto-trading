package com.project.user;

import com.project.configuration.security.JwtToken;
import com.project.exception.CustomException;
import com.project.user.entity.User;
import com.project.user.entity.dto.SignInDto;
import com.project.user.repository.UserRepository;
import com.project.reference.CheckUserReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


import static com.project.exception.ErrorCode.UNMATCHED_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

public class CheckUserReferenceTest {
    @InjectMocks
    private CheckUserReference checkUserReference;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtToken jwtToken;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    @DisplayName("비밀번호 확인 - 실패")
    void passwordFailTest() {
        //given
        SignInDto sign = SignInDto.builder()
                .email("email@naver.com")
                .password("password1")
                .build();

        User user = User.builder()
                .id(1L)
                .email("email@naver.com")
                .password(passwordEncoder.encode("password2"))
                .build();

        CustomException exception = assertThrows(CustomException.class, () -> checkUserReference.checkPassword(user, sign.getPassword()));

        assertEquals(UNMATCHED_PASSWORD, exception.getErrorCode());
    }
}
