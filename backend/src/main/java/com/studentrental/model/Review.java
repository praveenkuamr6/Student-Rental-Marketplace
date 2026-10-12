package com.studentrental.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="reviews", uniqueConstraints=@UniqueConstraint(columnNames={"booking_id","reviewer_id"}))
public class Review {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private Booking booking;
 @ManyToOne(optional=false) private AppUser reviewer;
 @ManyToOne(optional=false) private AppUser reviewee;
 @Column(nullable=false) private int rating;
 @Column(length=1200) private String comment;
 private LocalDateTime createdAt=LocalDateTime.now();
 public Review(){}
 public Long getId(){return id;} public Long getRating(){return (long)rating;} public void setRating(int v){rating=v;}
 public String getComment(){return comment;} public void setComment(String v){comment=v;}
 public Long getIdValue(){return id;} public Booking getBooking(){return booking;} public void setBooking(Booking v){booking=v;}
 public AppUser getReviewer(){return reviewer;} public void setReviewer(AppUser v){reviewer=v;}
 public AppUser getReviewee(){return reviewee;} public void setReviewee(AppUser v){reviewee=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
