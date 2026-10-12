package com.studentrental.repo;
import com.studentrental.model.Booking; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface BookingRepository extends JpaRepository<Booking,Long>{
 List<Booking> findByRenterIdOrOwnerIdOrderByCreatedAtDesc(Long renterId, Long ownerId);
 List<Booking> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
}
