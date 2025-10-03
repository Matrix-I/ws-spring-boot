package com.io.ws_demo;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {

  @Override
  public boolean beforeHandshake(
      org.springframework.http.server.ServerHttpRequest request,
      org.springframework.http.server.ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Map<String, Object> attributes)
      throws Exception {
    // Lấy token từ query param hoặc header
    String token = null;
    if (request instanceof ServletServerHttpRequest servletRequest) {
      HttpServletRequest httpRequest = servletRequest.getServletRequest();
      token = httpRequest.getParameter("token"); // wss://.../ws?token=JWT
    }

    if (token == null || !validateJwt(token)) {
      return false; // reject connection
    }

    // Nếu ok thì lưu user info vào attributes
    attributes.put("user", extractUserFromToken(token));
    return true;
  }

  @Override
  public void afterHandshake(
      org.springframework.http.server.ServerHttpRequest request,
      org.springframework.http.server.ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Exception exception) {}

  private boolean validateJwt(String token) {
    try {
      Jwts.parserBuilder()
          .setSigningKey("u/mt3YMWEyWSFS97ZHAxKrzwTJ0d9PsdQHb3z4TxOfg".getBytes())
          .build()
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  private String extractUserFromToken(String token) {
    return Jwts.parserBuilder()
        .setSigningKey("u/mt3YMWEyWSFS97ZHAxKrzwTJ0d9PsdQHb3z4TxOfg".getBytes())
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }
}
