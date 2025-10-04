package com.io.ws_demo;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class BroadcastService {

  private final SimpMessagingTemplate messagingTemplate;

  public BroadcastService(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  //  @Scheduled(initialDelay = 3, fixedRate = 2, timeUnit = TimeUnit.SECONDS)
  public void broadcastUpdate() {
    messagingTemplate.convertAndSend(
        "/topic/messages", "Hello World from broadcast each 2 seconds!");
  }
}
