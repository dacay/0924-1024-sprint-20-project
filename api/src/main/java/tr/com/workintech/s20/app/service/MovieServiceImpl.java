package tr.com.workintech.s20.app.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.workintech.s20.app.entity.Category;
import tr.com.workintech.s20.app.entity.Movie;
import tr.com.workintech.s20.app.entity.Review;
import tr.com.workintech.s20.app.repository.CategoryRepository;
import tr.com.workintech.s20.app.repository.MovieRepository;
import tr.com.workintech.s20.app.repository.ReviewRepository;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;
  private final CategoryRepository categoryRepository;
  private final ReviewRepository reviewRepository;

  public MovieServiceImpl(MovieRepository movieRepository, CategoryRepository categoryRepository, ReviewRepository reviewRepository) {
    this.movieRepository = movieRepository;
    this.categoryRepository = categoryRepository;
    this.reviewRepository = reviewRepository;
  }

  @Override
  public List<Movie> listMovies() {

    return this.movieRepository.findAll();
  }

  @Override
  public List<Movie> listMovies(String categoryStr) {

    Category category = this.categoryRepository.findByName(categoryStr);

    if (category == null) {

      log.debug("Category not found. Returning empty movie list...");

      return Collections.emptyList();
    }

    return category.getMovies();
  }

  @Override
  public Movie getMovie(long id) {

    return this.movieRepository.findById(id).orElse(null);
  }

  @Override
  public Movie addMovie(Movie movie) {

    return this.movieRepository.save(movie);
  }

  @Override
  public Movie updateMovie(long id, Movie movie) {

    movie.setId(id);

    this.movieRepository.save(movie);

    return movie;
  }

  @Override
  public void removeMovie(long id) {

    this.movieRepository.deleteById(id);
  }

  @Override
  public List<Category> listCategories() {

    return this.categoryRepository.findAll();
  }

  @Override
  public void addCategory(String name) {

    Category category = new Category();
    category.setName(name);

    this.categoryRepository.save(category);
  }

  @Override
  public void renameCategory(String oldName, String newName) {

    Category category = this.categoryRepository.findByName(oldName);

    if (category == null) {

      log.debug("Category not found, skipping rename...");

      return;
    }

    category.setName(newName);

    this.categoryRepository.save(category);
  }

  @Override
  public void removeCategory(String name) {

    this.categoryRepository.deleteByName(name);
  }

  @Override
  public List<Review> listReviews(Movie movie) {

    return this.reviewRepository.findAll();
  }

  @Override
  public void addReview(Review review) {

    this.reviewRepository.save(review);
  }

  @Override
  public void updateReview(long id, Review review) {

    review.setId(id);

    this.reviewRepository.save(review);
  }

  @Override
  public void removeReview(Long id) {

    this.reviewRepository.deleteById(id);
  }
}
