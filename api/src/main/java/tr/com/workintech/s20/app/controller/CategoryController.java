package tr.com.workintech.s20.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.com.workintech.s20.app.entity.Movie;
import tr.com.workintech.s20.app.repository.CategoryRepository;
import tr.com.workintech.s20.app.service.MovieService;

import java.util.List;

@RequestMapping("/categories")
@RestController
public class CategoryController {

  @Autowired
  private MovieService movieService;

  // /categories/:category/movies
  @GetMapping("/{category}/movies")
  public List<Movie> listMoviesInCategory(@PathVariable("category") String categoryName) {

    return this.movieService.listMovies(categoryName);
  }
}
