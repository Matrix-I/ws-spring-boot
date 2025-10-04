package com.io.ws_demo;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {

  @Override
  public boolean beforeHandshake(
      ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Map<String, Object> attributes) {
    String token = null;
    if (request instanceof ServletServerHttpRequest servletRequest) {
      HttpServletRequest httpRequest = servletRequest.getServletRequest();
      token = httpRequest.getParameter("token"); // wss://.../ws?token=JWT
    }

    if (token == null || !validateJwt(token)) {
      return false; // reject connection
    }

    // Should read token and extract username from it
    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(
            new UserPrincipal(extractUserFromToken(token)),
            null,
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

    // Put into attributes so the HandshakeHandler can set it as Principal
    attributes.put("SPRING_AUTH", authentication);
    return true;
  }

  @Override
  public void afterHandshake(
      ServerHttpRequest request,
      ServerHttpResponse response,
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
