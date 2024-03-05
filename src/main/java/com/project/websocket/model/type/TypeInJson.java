package com.project.websocket.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TypeInJson {
    TICKER("ticker"), TRADE("trade"), ORDER_BOOK("orderbook"), MY_TRADE("myTrade");

    private final String type;
}
