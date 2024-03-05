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
public class JsonDto {
    private String type;
    private List<String> codes;

    public JsonDto searchMarketTicker(List<String> marketNameList) {
        return JsonDto.builder()
                .type(TypeInJson.TICKER.getType())
                .codes(marketNameList)
                .build();
    }
}
