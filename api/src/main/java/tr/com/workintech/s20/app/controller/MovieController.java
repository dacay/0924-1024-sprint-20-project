package tr.com.workintech.s20.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tr.com.workintech.s20.app.dto.AddMovieRequest;
import tr.com.workintech.s20.app.dto.AddMovieResponse;
import tr.com.workintech.s20.app.dto.ListMoviesResponse;
import tr.com.workintech.s20.app.entity.Movie;
import tr.com.workintech.s20.app.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

  @Autowired
  private MovieService movieService;

  @GetMapping({"/", ""})
  public ListMoviesResponse listMovies() {

    List<Movie> movies = this.movieService.listMovies();

    return new ListMoviesResponse(movies);
  }

  @PostMapping("/")
  public AddMovieResponse addMovie(@RequestBody AddMovieRequest request) {

    Movie movie = new Movie();
    movie.setTitle(request.title());
    movie.setDescription(request.description());
    movie.setCategories(request.categories());

    Movie addedMovie = this.movieService.addMovie(movie);

    return new AddMovieResponse(addedMovie);
  }

  @GetMapping("/{id}")
  public Movie getMovie(@PathVariable("id") long id) {

    return this.movieService.getMovie(id);
  }

  @DeleteMapping("/{id}")
  public void deleteMovie(@PathVariable("id") long id) {

    this.movieService.removeMovie(id);
  }
}
