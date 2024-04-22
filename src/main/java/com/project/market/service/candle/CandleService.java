package com.project.market.service.candle;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.project.exception.CustomException;
import com.project.market.entity.Candle;
import com.project.market.entity.dto.CandleDto;
import com.project.market.entity.dto.CandleToNowDto;
import com.project.market.entity.dto.MinuteCandleDto;
import com.project.market.entity.dto.MinuteCandleToNowDto;
import com.project.reference.CheckMarketReference;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.project.exception.ErrorCode.MARKET_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CandleService {
    private final CheckMarketReference checkMarketReference;
    private final TransDateToString transDateToString;
    private static final String URL = "https://api.upbit.com/v1/candles/";
    private static final String[][] PERIOD_ARRAY = {{"days", "일"}, {"weeks", "주"}, {"months", "월"}};

    private List<Candle> searchCandle(String requestUrl) {
        List<Candle> candleList = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .addHeader("accept", "application/json")
                .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseData = response.toString();

                Gson gson = new Gson();
                JsonArray jsonArray = gson.fromJson(responseData, JsonArray.class);

                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    String market = jsonObject.get("market").getAsString();
                    String candleDateTimeKst = jsonObject.get("candle_date_time_kst").getAsString();
                    double openingPrice = jsonObject.get("opening_price").getAsDouble();
                    double highPrice = jsonObject.get("high_price").getAsDouble();
                    double lowPrice = jsonObject.get("low_price").getAsDouble();
                    double tradePrice = jsonObject.get("trade_price").getAsDouble();
                    double candleAccTradePrice = jsonObject.get("candle_acc_trade_price").getAsDouble();
                    double candleAccTradeVolume = jsonObject.get("candle_acc_trade_volume").getAsDouble();

                    Candle candle = Candle.builder()
                            .marketName(market)
                            .candleDateTime(candleDateTimeKst)
                            .openingPrice(openingPrice)
                            .highPrice(highPrice)
                            .lowPrice(lowPrice)
                            .tradePrice(tradePrice)
                            .allTradePrice(candleAccTradePrice)
                            .allTradeVolume(candleAccTradeVolume)
                            .build();

                    candleList.add(candle);
                }
            }
        } catch (IOException e) {
            throw new CustomException(MARKET_NOT_FOUND);
        }

        return candleList;
    }

    public List<Candle> generateCandleUrl (CandleDto dto) {
        String market = checkMarketReference.findByCoinName(dto.getMarketName()).getMarket();
        String period = dividePeriod(dto.getPeriod());
        String toDate = transDateToString.transDate(dto.getToDate());

        return searchCandle(URL + period + "?market=" + market + "&to=" + toDate + "&count=" + dto.getCount());
    }

    public List<Candle> generateCandleUrl (CandleToNowDto dto) {
        String market = checkMarketReference.findByCoinName(dto.getMarketName()).getMarket();
        String period = dividePeriod(dto.getPeriod());

        return searchCandle(URL + period + "?market=" + market + "&count=" + dto.getCount());
    }

    public List<Candle> generateMinuteCandleUrl (MinuteCandleDto dto) {
        String market = checkMarketReference.findByCoinName(dto.getMarketName()).getMarket();
        String toDate = transDateToString.transDate(dto.getToDate());

        return searchCandle(URL + "minute" + dto.getPeriod() + "?market=" + market + "&to=" + toDate + "&count=" + dto.getCount());
    }

    public List<Candle> generateMinuteCandleUrl (MinuteCandleToNowDto dto) {
        String market = checkMarketReference.findByCoinName(dto.getMarketName()).getMarket();

        return searchCandle(URL + "minute" + dto.getPeriod() + "?market=" + market + "&count=" + dto.getCount());
    }

    private String dividePeriod (String period) { // 검색하는 기간
        for (int i = 0; i < PERIOD_ARRAY.length; i++) {
            if (period.equals(PERIOD_ARRAY[i][1])) {
                return PERIOD_ARRAY[i][0];
            }
        }
        return null;
    }
}
