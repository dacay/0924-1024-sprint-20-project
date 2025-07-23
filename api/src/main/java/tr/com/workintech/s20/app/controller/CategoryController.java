package tr.com.workintech.s20.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tr.com.workintech.s20.app.dto.AddCategoryRequest;
import tr.com.workintech.s20.app.dto.AddCategoryResponse;
import tr.com.workintech.s20.app.entity.Category;
import tr.com.workintech.s20.app.entity.Movie;
import tr.com.workintech.s20.app.repository.CategoryRepository;
import tr.com.workintech.s20.app.service.MovieService;

import java.util.List;

@RequestMapping("/categories")
@RestController
public class CategoryController {

  @Autowired
  private MovieService movieService;

  @GetMapping({"/", ""})
  public List<Category> listCategories() {

    return this.movieService.listCategories();
  }

  // /categories/:category/movies
  @GetMapping("/{category}/movies")
  public List<Movie> listMoviesInCategory(@PathVariable("category") String categoryName) {

    return this.movieService.listMovies(categoryName);
  }

  @PostMapping({"/", ""})
  public AddCategoryResponse addCategory(@RequestBody AddCategoryRequest request) {

    Category category = this.movieService.addCategory(request.name());

    return new AddCategoryResponse(category);
  }
}
