package com.project.configuration.redis;

import org.springframework.data.repository.CrudRepository;


public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    RefreshToken findByEmail(String email);
    Boolean existByEmail(String email);
    RefreshToken findByRefreshToken(String refreshToken);
}
