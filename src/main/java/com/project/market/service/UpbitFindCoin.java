package com.project.market.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.project.market.entity.Market;
import com.project.market.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class UpbitFindCoin {
    private final MarketRepository marketRepository;
    private static final String findCoinUrl = "https://api.upbit.com/v1/market/all";

    public void addCoin () {
        /**
         * 원화 시장 코인 DB 에 우선 모두 추가 market : KRW-***
         * {"market" : "market_name",
         *  "korean_name" : "코인_한글명",
         *  "english_name" : "코인_영문명"
         *  }
         */
        try {
            URL url = new URL(findCoinUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-type", "application/json");
            conn.setRequestProperty("accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonArray parse = (JsonArray) JsonParser.parseString(result);
            for (int i = 0; i < parse.size(); i++) {
                JsonObject jsonObject = (JsonObject) parse.get(i);
                String market = jsonObject.getAsJsonObject().get("market").getAsString();
                String kor_name = jsonObject.getAsJsonObject().get("korean_name").getAsString();
                String eng_name = jsonObject.getAsJsonObject().get("english_name").getAsString();
                if (market.startsWith("KRW") && !marketRepository.existsByMarket(market)) { // 원화 코인만 DB 에 등록
                    Market coin = Market.builder()
                            .market(market)
                            .coinName(kor_name)
                            .coinEngName(eng_name)
                            .build();
                    System.out.println(coin.getCoinName() + " 추가");
                    marketRepository.save(coin);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
