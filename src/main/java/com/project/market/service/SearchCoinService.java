package com.project.market.service;

import com.project.market.entity.Market;
import com.project.market.repository.MarketRepository;
import com.project.websocket.model.WebSocketJsonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchCoinService {
    private final MarketRepository marketRepository;

    public Page<Market> pageAllMarket() {
        PageRequest pageable = PageRequest.of(0, 10);
        return marketRepository.findAll(pageable);
    }

    public List<String> transMarketNameToList (Page<Market> page) {
        // page 내 market 의 이름들을 모은 List 생성
        List<Market> marketList = page.getContent();
        List<String> marketNameToString = new ArrayList<>();

        for (Market market : marketList) {
            String marketName = market.getMarket();
            marketNameToString.add(marketName);
        }
        return marketNameToString;
    }

    public WebSocketJsonDto searchTicker(List<String> marketNameList) {
        WebSocketJsonDto jsonDto = new WebSocketJsonDto();
        jsonDto.searchMarketTicker(marketNameList);

        return jsonDto;
    }
}
