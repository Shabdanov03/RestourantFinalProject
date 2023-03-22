package peaksoft.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.subCategoryResponse.SubCategoryResponseById;
import peaksoft.dto.response.subCategoryResponse.SubCategoryResponseGroupCategory;
import peaksoft.dto.response.subCategoryResponse.SubcategoryResponse;
import peaksoft.entity.SubCategory;
import peaksoft.service.SubcategoryService;

import java.util.List;
import java.util.Map;

/**
 * Shabdanov Ilim
 **/
@RestController
@RequestMapping("/api/subCategories")

public class SubCategoryApi {
    private final SubcategoryService subcategoryService;

    @Autowired
    public SubCategoryApi(SubcategoryService subcategoryService) {
        this.subcategoryService = subcategoryService;
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    private SimpleResponse saveSubCategory(@RequestBody @Valid SubCategoryRequest subCategoryRequest) {
        return subcategoryService.saveSubcategory(subCategoryRequest);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping
    private List<SubcategoryResponse> getAllSubCategories() {
        return subcategoryService.getAllSubCategories();
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/order/{categoryId}")
    private List<SubcategoryResponse> getAllSubCategoryOrderByCategoryName(@PathVariable Long categoryId) {
        return subcategoryService.getAllSubCategoryOrderByCategoryName(categoryId);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/group")
    private Map<String,List<SubCategoryResponseGroupCategory>> groupingByCategories() {
        return subcategoryService.groupingByCategories();
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/{subCategoryId}")
    public SubCategoryResponseById getSubCategoryById(@PathVariable Long subCategoryId) {
        return subcategoryService.getSubCategoriesById(subCategoryId);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{subCategoryId}")
    public SimpleResponse deleteSubCategoryById(@PathVariable Long subCategoryId) {
        return subcategoryService.deleteSubCategoriesBiId(subCategoryId);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{subCategoryId}")
    public SimpleResponse updateSubCategory(@RequestBody SubCategory subCategory, @PathVariable Long subCategoryId) {
        return subcategoryService.updateSubCategory(subCategoryId, subCategory);
    }
}
