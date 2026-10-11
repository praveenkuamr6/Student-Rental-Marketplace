package com.studentrental.repo;
import com.studentrental.model.RentalItem; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ItemRepository extends JpaRepository<RentalItem,Long>{
 List<RentalItem> findByAvailableTrueOrderByCreatedAtDesc();
 List<RentalItem> findByOwnerIdOrderByCreatedAtDesc(Long ownerId);
}
