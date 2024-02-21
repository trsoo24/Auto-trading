package com.project.user.entity.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String type;
}
