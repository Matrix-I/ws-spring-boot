package com.io.ws_demo;

import java.security.Principal;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

  private static final Logger log = LoggerFactory.getLogger(ChatController.class);

  private final SimpMessagingTemplate messagingTemplate;

  public ChatController(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  //  /** Send to everyone except the current session */
  //  @MessageMapping("/chat")
  //  @SendTo("/topic/messages")
  //  public void handleMessage(
  //      String message, Principal principal, @Header("simpSessionId") String sessionId) {
  //    log.info("Received message: {} from session: {}", message, sessionId);
  //    String user =
  //        principal != null
  //            ? ((UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal())
  //                .name()
  //            : "anonymous";
  //    String payload = user + ": " + message;
  //
  //    messagingTemplate.convertAndSend(
  //        "/topic/messages", payload, Map.of(SimpMessageHeaderAccessor.SESSION_ID_HEADER, user));
  //  }

  /** Send to everyone */
  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public String handleMessage(
      String message, Principal principal, @Header("simpSessionId") String sessionId) {
    log.info("Received message: {} from session: {}", message, sessionId);
    String user =
        principal != null
            ? ((UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal())
                .name()
            : "anonymous";

    return user + ": " + message;
  }
}
