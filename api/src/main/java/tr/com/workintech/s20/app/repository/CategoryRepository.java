package tr.com.workintech.s20.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tr.com.workintech.s20.app.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  @Query("SELECT c FROM Category c WHERE name = :name")
  Category findByName(@Param("name") String name);

  @Query("DELETE FROM Category c WHERE name = :name")
  void deleteByName(@Param("name") String name);
}
