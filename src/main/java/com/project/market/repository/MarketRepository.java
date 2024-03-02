package com.project.market.repository;

import com.project.market.entity.Market;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {
    boolean existsByMarket(String market);
    Optional<Market> findByMarket(String market);
    Optional<Market> findByCoinName(String coinName);
    Optional<Market> findByCoinEngName(String coinEngName);

    Page<Market> findAll(Pageable pageable); // 화면 내 페이징 가격 조회
}
