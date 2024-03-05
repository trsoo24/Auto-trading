package com.project.websocket.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebsocketJson {
    private String ticket;
    private String type;
    private List<String> codes;

    public void setTicket() {
        this.ticket = UUID.randomUUID().toString().substring(0, 8);
    }

    public void addCodeList(String code) {
        this.codes.add(code);
    }

    public String generateCodes(WebsocketJson websocketJson) {
        JsonArray codesArray = new JsonArray();
        for (String code : websocketJson.codes) {
            codesArray.add(code);
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ticket", websocketJson.getTicket());
        jsonObject.addProperty("type", websocketJson.getType());
        jsonObject.add("codes", codesArray);

        return jsonObject.toString();
    }
}
