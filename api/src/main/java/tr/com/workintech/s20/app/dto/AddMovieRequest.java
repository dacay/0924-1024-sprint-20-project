package tr.com.workintech.s20.app.dto;

import tr.com.workintech.s20.app.entity.Category;

import java.util.Set;

public record AddMovieRequest(String title, String description, Set<Category> categories) {
}
