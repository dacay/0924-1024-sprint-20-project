package tr.com.workintech.s20.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tr.com.workintech.s20.app.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
