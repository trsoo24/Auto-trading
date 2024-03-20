package com.project.configuration.redis;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "refreshToken")
public class RefreshToken {
    @Id
    private String id;
    @Indexed
    private String email;
    private String refreshToken;
    @TimeToLive
    private Long expiration;
}
