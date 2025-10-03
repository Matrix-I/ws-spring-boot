package com.io.ws_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Optional;

public class SimpleTextHandler extends TextWebSocketHandler {
  private static final Logger log = LoggerFactory.getLogger(SimpleTextHandler.class);

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    log.info("WS connected: {}", session.getId());
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    log.info("WS [{}] received: {}", session.getId(), message.getPayload());
    log.info(
        "WS [{}] and token received: {}",
        session.getId(),
        Optional.ofNullable(session.getUri())
            .map(URI::getQuery)
            .map(param -> param.split("=")[1])
            .orElse(""));
    session.sendMessage(new TextMessage("Echo: " + message.getPayload()));
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
    log.warn(
        "WS transport error [{}]: {}",
        session != null ? session.getId() : "n/a",
        exception.getMessage(),
        exception);
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    log.info("WS closed [{}]: {}", session.getId(), status);
  }
}
