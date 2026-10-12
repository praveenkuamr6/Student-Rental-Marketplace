package com.studentrental.repo;
import com.studentrental.model.Review; import org.springframework.data.jpa.repository.JpaRepository;
public interface ReviewRepository extends JpaRepository<Review,Long>{ boolean existsByBookingIdAndReviewerId(Long bookingId,Long reviewerId); }
