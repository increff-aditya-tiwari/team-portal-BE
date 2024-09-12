package com.increff.teamer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.pojo.NotificationPojo;
import com.increff.teamer.security.WebSocketConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
//    private static List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private static final ConcurrentHashMap<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Principal userPrincipal = session.getPrincipal();
        if (userPrincipal != null) {
            String username = userPrincipal.getName();
            userSessions.put(username, session);
            System.out.println("User connected: " + username );
            System.out.println("with this session "+session.getId());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("we are removing the connection for user " + Objects.requireNonNull(session.getPrincipal()).getName() + " with stats " + status);
        System.out.println("with this session "+session.getId());
        userSessions.remove(session.getPrincipal().getName());
//        session.close();
    }

    public void closeConnection(String username) throws CommonApiException{
        System.out.println("going to remove connection");
        WebSocketSession session = userSessions.get(username);

        try {
            session.close();
//            afterConnectionClosed(session,new CloseStatus(1000));
        }catch (Exception e){
            throw new  CommonApiException(HttpStatus.INTERNAL_SERVER_ERROR,"Not able to close the websocket connection");
        }
    }
    public void sendMessageToUsers(List<String> userNameList, NotificationPojo notificationPojo) throws CommonApiException {
        for(String username : userNameList){
            WebSocketSession session = userSessions.get(username);
            if (session != null && session.isOpen()) {
                try {
                    String jsonMessage = objectMapper.writeValueAsString(notificationPojo);
                    session.sendMessage(new TextMessage(jsonMessage));
                    System.out.println("message sent to user "+session.getPrincipal().getName());
                    System.out.println("with session "+session.getId());
                } catch (Exception e) {
                    System.out.println("Error sending message to " + username + ": " + e.getMessage());
                    throw new CommonApiException(HttpStatus.INTERNAL_SERVER_ERROR,"Error sending message to " + username);
                }
            } else {
                System.out.println("Session is null or closed for user " + username);
            }
        }
    }
}
