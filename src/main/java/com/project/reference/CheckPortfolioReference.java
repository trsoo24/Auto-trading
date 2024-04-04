package com.project.reference;

import com.project.exception.CustomException;
import com.project.market.repository.MarketRepository;
import com.project.portfolio.entity.Portfolio;
import com.project.portfolio.repository.PortfolioRepository;
import com.project.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.exception.ErrorCode.NOT_OWN_CURRENCY;

@Service
@RequiredArgsConstructor
public class CheckPortfolioReference {
    private final PortfolioRepository portfolioRepository;
    private final CheckMarketReference checkMarketReference;

    public Portfolio findByUserAndMarketName(User user, String marketName) {
        String marketCode = checkMarketReference.findByCoinName(marketName).getCoinName(); // 한글로 검색했을 때
        return portfolioRepository.findByUserAndMarketName(user, marketCode)
                .orElseThrow(() -> new CustomException(NOT_OWN_CURRENCY));
    }

    public boolean existsByUserAndMarketName(User user, String marketName) {
        return portfolioRepository.existsByUserAndMarketName(user, marketName);
    }
}
