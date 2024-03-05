package com.project.reference;

import com.project.exception.CustomException;
import com.project.market.entity.Market;
import com.project.market.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.exception.ErrorCode.INCORRECT_SEARCH_METHOD;
import static com.project.exception.ErrorCode.MARKET_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CheckMarketReference {
    private final MarketRepository marketRepository;

    public Market findByMarketCode(String marketCode) {
        return marketRepository.findByMarket(marketCode)
                .orElseThrow(() -> new CustomException(MARKET_NOT_FOUND));
    }

    public Market findByCoinName(String coinName) {
        return marketRepository.findByCoinName(coinName)
                .orElseThrow(() -> new CustomException(INCORRECT_SEARCH_METHOD));
    }
}
