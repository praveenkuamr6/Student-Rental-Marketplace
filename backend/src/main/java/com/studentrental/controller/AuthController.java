package com.studentrental.controller;
import com.studentrental.model.*; import com.studentrental.repo.UserRepository; import com.studentrental.security.JwtService; import com.studentrental.service.CurrentUser;
import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;private final CurrentUser current;
 public AuthController(UserRepository u,PasswordEncoder e,JwtService j,CurrentUser c){users=u;encoder=e;jwt=j;current=c;}
 record Register(@NotBlank String name,@Email @NotBlank String email,@Size(min=8) String password,@NotBlank String college){}
 record Login(@Email @NotBlank String email,@NotBlank String password){}
 private Map<String,Object> dto(AppUser u,String token){return Map.of("token",token,"user",userDto(u));}
 public static Map<String,Object> userDto(AppUser u){return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"college",u.getCollege(),"role",u.getRole(),"verified",u.isVerified());}
 @PostMapping("/register") ResponseEntity<?> register(@Valid @RequestBody Register r){
  if(users.existsByEmail(r.email().toLowerCase()))return ResponseEntity.badRequest().body(Map.of("message","Email is already registered"));
  AppUser u=new AppUser();u.setName(r.name().trim());u.setEmail(r.email().toLowerCase().trim());u.setPassword(encoder.encode(r.password()));u.setCollege(r.college().trim());users.save(u);return ResponseEntity.ok(dto(u,jwt.generate(u.getEmail())));
 }
 @PostMapping("/login") ResponseEntity<?> login(@Valid @RequestBody Login r){return users.findByEmail(r.email().toLowerCase().trim()).filter(u->encoder.matches(r.password(),u.getPassword())).<ResponseEntity<?>>map(u->ResponseEntity.ok(dto(u,jwt.generate(u.getEmail())))).orElseGet(()->ResponseEntity.status(401).body(Map.of("message","Invalid email or password")));}
 @GetMapping("/me") Map<String,Object> me(){return userDto(current.get());}
}
