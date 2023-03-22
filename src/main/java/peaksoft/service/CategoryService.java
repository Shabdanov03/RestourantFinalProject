package peaksoft.service;

import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.categoryResponse.CategoryResponse;
import peaksoft.dto.response.categoryResponse.CategoryResponseById;
import peaksoft.dto.response.restaurantResponse.RestaurantResponseById;
import peaksoft.entity.Category;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface CategoryService {
    SimpleResponse saveCategory(CategoryRequest categoryRequest);
    List<CategoryResponse> getAllCategories();
    CategoryResponseById getCategoriesById(Long id);
    SimpleResponse deleteCategoriesBiId(Long id);
    SimpleResponse updateCategory(Long id, Category category);

}
