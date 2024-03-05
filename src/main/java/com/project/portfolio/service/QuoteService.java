package com.project.portfolio.service;

import com.project.market.entity.Ticker;
import com.project.market.service.TickerService;
import com.project.portfolio.entity.Portfolio;
import com.project.portfolio.entity.dto.Quote;
import com.project.portfolio.repository.PortfolioRepository;
import com.project.reference.CheckUserReference;
import com.project.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteService { // 포트폴리오 전체 시세 조회
    private final CheckUserReference checkUserReference;
    private final PortfolioRepository portfolioRepository;
    private final TickerService tickerService;

    @Transactional
    public List<Quote> getPortfolioQuoteList(HttpServletRequest request) {
        String email = checkUserReference.checkUserReference(request);
        User user = checkUserReference.findUserByEmail(email);

        List<Portfolio> portfolioList = portfolioRepository.findAllByUserId(user.getId());

        StringBuilder sb = new StringBuilder();
        for (Portfolio portfolio : portfolioList) {
            sb.append(portfolio.getMarketName());
            sb.append(",");
        }
        List<Ticker> tickerList = tickerService.searchCoinTicker(tickerService.parseMarketNameList(sb.toString()));

        HashMap<String , Double> marketValueMap = filterQuote(tickerList);

        List<Quote> quoteList = new ArrayList<>();

        for (Portfolio portfolio : portfolioList) {
            double nowPrice = marketValueMap.get(portfolio.getMarketName());
            double profitLossAmount = nowPrice * portfolio.getVolume() - portfolio.getPrice();
            double profitLossPercent = profitLossAmount / portfolio.getPrice();

            Quote quote = Quote.builder()
                    .marketName(portfolio.getMarketName())
                    .quote(nowPrice)
                    .averageValue(portfolio.getAverageValue())
                    .volume(portfolio.getVolume())
                    .profitLossAmount(profitLossAmount)
                    .profitLossPercent(profitLossPercent)
                    .lastTradeTimeStamp(portfolio.getLastTradeTimeStamp())
                    .build();

            quoteList.add(quote);
        }

        return quoteList;
    }

    public HashMap<String, Double> filterQuote (List<Ticker> tickerList) {
        HashMap<String, Double> marketValueMap = new HashMap<>();

        for (Ticker ticker : tickerList) {
            marketValueMap.put(ticker.getMarketName(), ticker.getTradePrice());
        }

        return marketValueMap;
    }
}
