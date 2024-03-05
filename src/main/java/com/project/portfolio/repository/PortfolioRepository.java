package com.project.portfolio.repository;

import com.project.portfolio.entity.Portfolio;
import com.project.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findAllByUserId(Long userId);

    Optional<Portfolio> findByUserAndMarketName(User user, String marketName);

    boolean existsByUserAndMarketName(User user, String marketName);
}
