package peaksoft.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.categoryResponse.CategoryResponse;
import peaksoft.dto.response.categoryResponse.CategoryResponseById;
import peaksoft.entity.Category;
import peaksoft.service.CategoryService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@RestController
@RequestMapping("/api/categories")
public class CategoryApi {
    private final CategoryService categoryService;

    public CategoryApi(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF','WALTER')")
    @GetMapping
    public List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{categoryId}")
    public CategoryResponseById getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoriesById(categoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public SimpleResponse deleteCategoryById(@PathVariable Long categoryId){
        return categoryService.deleteCategoriesBiId(categoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{categoryId}")
    public SimpleResponse updateCategory(@RequestBody Category category, @PathVariable Long categoryId){
        return categoryService.updateCategory(categoryId,category);
    }
}
