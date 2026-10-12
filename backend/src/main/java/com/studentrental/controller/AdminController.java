package com.studentrental.controller;
import com.studentrental.model.*;import com.studentrental.repo.*;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/admin")
public class AdminController{
 final UserRepository users;final ItemRepository items;final BookingRepository bookings;
 public AdminController(UserRepository u,ItemRepository i,BookingRepository b){users=u;items=i;bookings=b;}
 Map<String,Object> user(AppUser u){return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"college",u.getCollege(),"role",u.getRole(),"verified",u.isVerified(),"createdAt",u.getCreatedAt());}
 Map<String,Object> item(RentalItem x){return Map.of("id",x.getId(),"title",x.getTitle(),"category",x.getCategory(),"pricePerDay",x.getPricePerDay(),"available",x.isAvailable(),"ownerName",x.getOwner().getName(),"ownerId",x.getOwner().getId());}
 @GetMapping("/stats") Map<String,Object> stats(){return Map.of("users",users.count(),"students",users.findAll().stream().filter(u->u.getRole()==Role.STUDENT).count(),"items",items.count(),"bookings",bookings.count(),"pendingBookings",bookings.findAll().stream().filter(b->b.getStatus()==Booking.Status.PENDING).count());}
 @GetMapping("/users") List<Map<String,Object>> allUsers(){return users.findAll().stream().map(this::user).toList();}
 @PatchMapping("/users/{id}/verify") ResponseEntity<?> verify(@PathVariable Long id,@RequestBody Map<String,Boolean>b){AppUser u=users.findById(id).orElse(null);if(u==null)return ResponseEntity.notFound().build();if(u.getRole()==Role.ADMIN)return ResponseEntity.badRequest().body(Map.of("message","Admin verification cannot be changed here"));u.setVerified(Boolean.TRUE.equals(b.get("verified")));return ResponseEntity.ok(user(users.save(u)));}
 @GetMapping("/items") List<Map<String,Object>> allItems(){return items.findAll().stream().map(this::item).toList();}
 @DeleteMapping("/items/{id}") ResponseEntity<?> deleteItem(@PathVariable Long id){if(!items.existsById(id))return ResponseEntity.notFound().build();items.deleteById(id);return ResponseEntity.noContent().build();}
}
