package com.studentrental.config;
import com.studentrental.model.*; import com.studentrental.repo.UserRepository; import com.studentrental.repo.ItemRepository;
import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class SeedData {
 @Bean CommandLineRunner seed(UserRepository users,ItemRepository items,PasswordEncoder encoder){return args->{
  if(!users.existsByEmail("admin@rental.local")){AppUser a=new AppUser();a.setName("Marketplace Admin");a.setEmail("admin@rental.local");a.setCollege("Platform");a.setRole(Role.ADMIN);a.setVerified(true);a.setPassword(encoder.encode("Admin@12345"));users.save(a);}
  AppUser student=users.findByEmail("student@rental.local").orElseGet(()->{AppUser u=new AppUser();u.setName("Aarav Sharma");u.setEmail("student@rental.local");u.setCollege("Campus Demo University");u.setVerified(true);u.setPassword(encoder.encode("Student@12345"));return users.save(u);});
  if(items.count()==0){
   add(items,student,"Engineering Mathematics Textbook","Clean copy, useful for first and second year engineering courses.","Books",25,100,"Good","https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=900","Main campus library");
   add(items,student,"Scientific Calculator","Casio-style scientific calculator, all functions tested and working.","Electronics",35,200,"Like new","https://images.unsplash.com/photo-1587145820266-a5951ee6f620?w=900","Engineering block");
   add(items,student,"Hybrid Bicycle","Comfortable campus commute bicycle with lock included.","Cycles",80,500,"Good","https://images.unsplash.com/photo-1485965120184-e220f721d03e?w=900","North hostel gate");
   add(items,student,"Desk Study Lamp","LED desk lamp with adjustable brightness for late-night study.","Hostel essentials",20,100,"Like new","https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=900","Student residence");
   add(items,student,"Arduino Starter Kit","Starter electronics kit for mini projects; components counted and packed.","Project tools",60,300,"Good","https://images.unsplash.com/photo-1518770660439-4636190af475?w=900","Innovation lab");
  }
 };}
 private void add(ItemRepository repo,AppUser owner,String title,String description,String category,double price,double deposit,String condition,String image,String location){RentalItem i=new RentalItem();i.setOwner(owner);i.setTitle(title);i.setDescription(description);i.setCategory(category);i.setPricePerDay(price);i.setDeposit(deposit);i.setConditionLabel(condition);i.setImageUrl(image);i.setLocation(location);repo.save(i);}
}
