package com.project.user.controller;

import com.project.user.entity.User;
import com.project.user.entity.dto.SignInDto;
import com.project.user.entity.dto.SignUpDto;
import com.project.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/join")
    public ResponseEntity<User> signUp (@RequestBody @Valid SignUpDto sign) {
        return ResponseEntity.ok(userService.signUp(sign));
    }

    @PostMapping("/login")
    public ResponseEntity<String> signIn (@RequestBody @Valid SignInDto sign) {
        return ResponseEntity.ok(userService.signIn(sign));
    }
}
