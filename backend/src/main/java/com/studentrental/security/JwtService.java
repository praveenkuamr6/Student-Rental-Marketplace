package com.studentrental.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;
import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.util.Date;
@Service
public class JwtService {
 private final SecretKey key; private final long expiration;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){
  byte[] bytes=secret.getBytes(StandardCharsets.UTF_8); if(bytes.length<32) throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes");
  this.key=Keys.hmacShaKeyFor(bytes); this.expiration=expiration;
 }
 public String generate(String email){return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expiration)).signWith(key).compact();}
 public String email(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
 public boolean valid(String token){try{return email(token)!=null;}catch(JwtException|IllegalArgumentException ex){return false;}}
}
