package tr.com.workintech.s20.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tr.com.workintech.s20.app.dto.AddMovieRequest;
import tr.com.workintech.s20.app.dto.AddMovieResponse;
import tr.com.workintech.s20.app.dto.ListMoviesResponse;
import tr.com.workintech.s20.app.entity.Movie;
import tr.com.workintech.s20.app.entity.User;
import tr.com.workintech.s20.app.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

  @Autowired
  private MovieService movieService;

  @GetMapping({"/", ""})
  @CrossOrigin
  // Bu sekilde, giris yapmis kullanicinin kendisine erisebiliyoruz
  // JwtAuthentication'da getPrincipal'da User dondugumuz icin @AuthenticationPrincipal ile
  // giris yapmis kullanicinin User'ini alabiliriz
  // Eger uzerinde ekstra degerli bilgiler varsa, annotation olmadan JwtAuthentication objesi de alabilir
  public ListMoviesResponse listMovies(@AuthenticationPrincipal User user) {

    log.debug("User: {}", user);

    List<Movie> movies = this.movieService.listMovies();

    return new ListMoviesResponse(movies);
  }

  @PostMapping({"/", ""})
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

  @PutMapping("/{id}")
  public Movie updateMovie(@PathVariable("id") long id, @RequestBody Movie movie) {

    Movie updateMovie = this.movieService.updateMovie(id, movie);

    return updateMovie;
  }

  @DeleteMapping("/{id}")
  public void deleteMovie(@PathVariable("id") long id) {

    this.movieService.removeMovie(id);
  }
}
