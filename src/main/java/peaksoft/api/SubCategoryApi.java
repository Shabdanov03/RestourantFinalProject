package peaksoft.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveSubCategory(@RequestBody @Valid SubCategoryRequest subCategoryRequest) {
        return subcategoryService.saveSubcategory(subCategoryRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<SubcategoryResponse> getAllSubCategories() {
        return subcategoryService.getAllSubCategories();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/order/{categoryId}")
    public List<SubcategoryResponse> getAllSubCategoryOrderByCategoryName(@PathVariable Long categoryId) {
        return subcategoryService.getAllSubCategoryOrderByCategoryName(categoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/group")
    public Map<String,List<SubCategoryResponseGroupCategory>> groupingByCategories() {
        return subcategoryService.groupingByCategories();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{subCategoryId}")
    public SubCategoryResponseById getSubCategoryById(@PathVariable Long subCategoryId) {
        return subcategoryService.getSubCategoriesById(subCategoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{subCategoryId}")
    public SimpleResponse deleteSubCategoryById(@PathVariable Long subCategoryId) {
        return subcategoryService.deleteSubCategoriesBiId(subCategoryId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{subCategoryId}")
    public SimpleResponse updateSubCategory(@RequestBody SubCategory subCategory, @PathVariable Long subCategoryId) {
        return subcategoryService.updateSubCategory(subCategoryId, subCategory);
    }
}
