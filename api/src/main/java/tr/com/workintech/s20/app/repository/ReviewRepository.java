package tr.com.workintech.s20.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.workintech.s20.app.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
