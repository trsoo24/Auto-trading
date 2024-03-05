//package com.project.websocket;
//
//import jakarta.websocket.*;
//
//import java.net.URI;

//@ClientEndpoint
//public class WebsocketClient {
//
//    Session userSession = null;
//
//    public WebsocketClient(URI endpointURI) {
//        try {
//            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
//            container.connectToServer(this, endpointURI);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @OnOpen
//    public void onOpen(Session userSession) {
//        System.out.println("Opening websocket");
//        this.userSession = userSession;
//    }
//
//    @OnClose
//    public void onClose(Session userSession, CloseReason reason) {
//        System.out.println("Closing websocket");
//        this.userSession = null;
//    }
//
//    @OnMessage
//    public void onMessage(String message) {
//        System.out.println("Received message: " + message);
//    }
//
//
//    public void sendMessage(String message) {
//        this.userSession.getAsyncRemote().sendText(message);
//    }
//
//    public static void main(String[] args) {
//        try {
//            // wss://api.upbit.com/websocket/v1 은 업비트의 WebSocket 주소입니다.
//            final WebsocketClient clientEndPoint = new WebsocketClient(new URI("wss://api.upbit.com/websocket/v1"));
//
//            // BTC/KRW의 현재 가격을 받아오는 메시지를 보냅니다.
//            clientEndPoint.sendMessage("[{\"ticket\":\"UNIQUE_TICKET\"},{\"type\":\"ticker\", \"codes\":[\"KRW-BTC\"]}]");
//        } catch (Exception ex) {
//            System.err.println("WebSocket Connection Exception: " + ex.getMessage());
//        }
//    }
//}
