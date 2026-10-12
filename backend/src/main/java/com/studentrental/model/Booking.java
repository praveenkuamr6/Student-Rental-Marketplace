package com.studentrental.model;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity @Table(name="bookings")
public class Booking {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private RentalItem item;
 @ManyToOne(optional=false) private AppUser renter;
 @ManyToOne(optional=false) private AppUser owner;
 @Column(nullable=false) private LocalDate startDate;
 @Column(nullable=false) private LocalDate endDate;
 @Column(nullable=false) private double totalAmount;
 @Column(nullable=false) private double depositAmount;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.PENDING;
 private LocalDateTime createdAt=LocalDateTime.now();
 public enum Status { PENDING, APPROVED, REJECTED, COMPLETED }
 public Booking(){}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public RentalItem getItem(){return item;} public void setItem(RentalItem v){item=v;}
 public AppUser getRenter(){return renter;} public void setRenter(AppUser v){renter=v;}
 public AppUser getOwner(){return owner;} public void setOwner(AppUser v){owner=v;}
 public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;}
 public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;}
 public double getTotalAmount(){return totalAmount;} public void setTotalAmount(double v){totalAmount=v;}
 public double getDepositAmount(){return depositAmount;} public void setDepositAmount(double v){depositAmount=v;}
 public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
