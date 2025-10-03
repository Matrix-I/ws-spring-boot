package com.io.ws_demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public String handle(String message) {
    return "Hello, " + message;
  }
}
