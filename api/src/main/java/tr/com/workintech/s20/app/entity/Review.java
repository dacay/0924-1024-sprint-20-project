package tr.com.workintech.s20.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "reviews")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private int score;
  private String text;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public void setScore(int score) {

    if (score > 10)
      this.score = 10;
    else if (score < 1)
      this.score = 1;
    else
      this.score = score;
  }
}
