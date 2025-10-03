package com.io.ws_demo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

  @GetMapping("/token")
  public String generateToken() {
    String SECRET_KEY = "u/mt3YMWEyWSFS97ZHAxKrzwTJ0d9PsdQHb3z4TxOfg";
    return Jwts.builder()
        .setSubject("user123")
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + 3600_000))
        .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
        .compact();
  }
}
