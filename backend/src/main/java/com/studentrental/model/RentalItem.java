package com.studentrental.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity @Table(name="rental_items")
public class RentalItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(nullable=false) private String title;
 @Column(nullable=false,length=2000) private String description;
 @Column(nullable=false) private String category;
 @Column(nullable=false) private double pricePerDay;
 private double deposit;
 private String conditionLabel="Good";
 private String imageUrl;
 private String location;
 @Column(nullable=false) private boolean available=true;
 @ManyToOne(optional=false) private AppUser owner;
 private LocalDateTime createdAt=LocalDateTime.now();
 public RentalItem(){}
 public Long getId(){return id;} public void setId(Long v){id=v;}
 public String getTitle(){return title;} public void setTitle(String v){title=v;}
 public String getDescription(){return description;} public void setDescription(String v){description=v;}
 public String getCategory(){return category;} public void setCategory(String v){category=v;}
 public double getPricePerDay(){return pricePerDay;} public void setPricePerDay(double v){pricePerDay=v;}
 public double getDeposit(){return deposit;} public void setDeposit(double v){deposit=v;}
 public String getConditionLabel(){return conditionLabel;} public void setConditionLabel(String v){conditionLabel=v;}
 public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
 public String getLocation(){return location;} public void setLocation(String v){location=v;}
 public boolean isAvailable(){return available;} public void setAvailable(boolean v){available=v;}
 public AppUser getOwner(){return owner;} public void setOwner(AppUser v){owner=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
