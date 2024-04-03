package com.project.websocket.model;

import com.project.websocket.model.type.TypeInJson;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebSocketJsonDto {
    private String type;
    private List<String> codes;

    public WebSocketJsonDto searchMarketTicker(List<String> marketNameList) {
        return WebSocketJsonDto.builder()
                .type(TypeInJson.TICKER.getType())
                .codes(marketNameList)
                .build();
    }
}
