package tr.com.workintech.s20.app.dto;

import tr.com.workintech.s20.app.entity.Movie;

import java.util.List;

public record ListMoviesResponse(List<Movie> movies) {
}
