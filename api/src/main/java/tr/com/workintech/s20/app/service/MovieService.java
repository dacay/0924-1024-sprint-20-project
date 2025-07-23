package tr.com.workintech.s20.app.service;

import tr.com.workintech.s20.app.entity.Category;
import tr.com.workintech.s20.app.entity.Movie;
import tr.com.workintech.s20.app.entity.Review;
import tr.com.workintech.s20.app.entity.User;

import java.util.List;

public interface MovieService {

  List<Movie> listMovies();

  List<Movie> listMovies(String category);

  Movie getMovie(long id);

  Movie addMovie(Movie movie);

  Movie updateMovie(long id, Movie movie);

  void removeMovie(long id);

  List<Category> listCategories();

  Category addCategory(String name);

  void renameCategory(String oldName, String newName);

  void removeCategory(String name);

  List<Review> listReviews(Movie movie);

  void addReview(Review review);

  void updateReview(long id, Review review);

  void removeReview(Long id);
}
