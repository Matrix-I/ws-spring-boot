package com.io.ws_demo;

import java.security.Principal;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class StompEventListener {

  @EventListener
  public void handleDisconnect(SessionDisconnectEvent event) {

    String sessionId = event.getSessionId();
    Principal user = event.getUser();

    System.out.println(
        "STOMP disconnected: " + sessionId + (user != null ? " user=" + user.getName() : ""));
  }
}
