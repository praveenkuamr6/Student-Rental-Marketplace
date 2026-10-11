package com.studentrental.service;
import com.studentrental.model.AppUser; import com.studentrental.repo.UserRepository;
import org.springframework.security.core.Authentication; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component;
@Component
public class CurrentUser {
 private final UserRepository users; public CurrentUser(UserRepository users){this.users=users;}
 public AppUser get(){Authentication a=SecurityContextHolder.getContext().getAuthentication();if(a==null)throw new RuntimeException("Login required");return users.findByEmail(a.getName()).orElseThrow(()->new RuntimeException("User not found"));}
}
