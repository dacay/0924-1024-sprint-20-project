package tr.com.workintech.s20.app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String title;
  private String description;
  @ManyToMany
  @JoinTable(
          name = "movies_categories",
          joinColumns = { @JoinColumn(name = "movie_id") },
          inverseJoinColumns = { @JoinColumn(name = "category_id") }
  )
  private Set<Category> categories;
}
