package com.studentrental.controller;
import com.studentrental.model.*;import com.studentrental.repo.*;import com.studentrental.service.CurrentUser;
import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/reviews")
public class ReviewController{
 final ReviewRepository reviews;final BookingRepository bookings;final UserRepository users;final CurrentUser current;
 public ReviewController(ReviewRepository r,BookingRepository b,UserRepository u,CurrentUser c){reviews=r;bookings=b;users=u;current=c;}
 @GetMapping List<Map<String,Object>> all(){return reviews.findAll().stream().map(r->Map.<String,Object>of("id",r.getIdValue(),"rating",r.getRating(),"comment",r.getComment()==null?"":r.getComment(),"reviewerName",r.getReviewer().getName(),"revieweeName",r.getReviewee().getName(),"bookingId",r.getBooking().getId())).toList();}
 @PostMapping ResponseEntity<?> create(@RequestBody Map<String,Object>b){try{AppUser me=current.get();Booking bk=bookings.findById(Long.valueOf(b.get("bookingId").toString())).orElseThrow();int rating=Integer.parseInt(b.get("rating").toString());if(bk.getStatus()!=Booking.Status.COMPLETED||(!bk.getRenter().getId().equals(me.getId())&&!bk.getOwner().getId().equals(me.getId()))||reviews.existsByBookingIdAndReviewerId(bk.getId(),me.getId())||rating<1||rating>5)return ResponseEntity.badRequest().body(Map.of("message","Review requires a completed rental; rating must be 1-5 and one review per user."));Review r=new Review();r.setBooking(bk);r.setReviewer(me);r.setReviewee(bk.getRenter().getId().equals(me.getId())?bk.getOwner():bk.getRenter());r.setRating(rating);r.setComment(Objects.toString(b.get("comment"),""));reviews.save(r);return ResponseEntity.ok(Map.of("message","Review submitted"));}catch(Exception e){return ResponseEntity.badRequest().body(Map.of("message","Could not submit review"));}}
}
