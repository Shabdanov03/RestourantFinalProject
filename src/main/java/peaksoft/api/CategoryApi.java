package peaksoft.api;

import jakarta.annotation.security.RolesAllowed;
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

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    private SimpleResponse saveCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.saveCategory(categoryRequest);
    }

    @RolesAllowed({"ROLE_ADMIN","ROLE_CHEF","ROLE_WALTER"})
    @GetMapping
    private List<CategoryResponse> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/{categoryId}")
    public CategoryResponseById getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategoriesById(categoryId);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{categoryId}")
    public SimpleResponse deleteCategoryById(@PathVariable Long categoryId){
        return categoryService.deleteCategoriesBiId(categoryId);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{categoryId}")
    public SimpleResponse updateCategory(@RequestBody Category category, @PathVariable Long categoryId){
        return categoryService.updateCategory(categoryId,category);
    }
}
