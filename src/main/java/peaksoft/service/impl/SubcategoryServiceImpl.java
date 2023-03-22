package peaksoft.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.subCategoryResponse.SubCategoryResponseById;
import peaksoft.dto.response.subCategoryResponse.SubCategoryResponseGroupCategory;
import peaksoft.dto.response.subCategoryResponse.SubcategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.CategoryRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.SubcategoryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Shabdanov Ilim
 **/
@Service
public class SubcategoryServiceImpl implements SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public SubcategoryServiceImpl(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public SimpleResponse saveSubcategory(SubCategoryRequest subCategoryRequest) {
        if (subcategoryRepository.existsByName(subCategoryRequest.name())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("SubCategory with name: %s already exists!", subCategoryRequest.name()))
                    .build();
        }

        Category category = categoryRepository.findById(subCategoryRequest.categoryId()).orElseThrow(() ->
                new NotFoundException("Restaurant with id: " + subCategoryRequest.categoryId() + " not found"));


        SubCategory subCategory = SubCategory.builder()
                .name(subCategoryRequest.name())
                .category(category)
                .build();
        subcategoryRepository.save(subCategory);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("SubCategory successfully saved in category!")
                .build();
    }

    @Override
    public List<SubcategoryResponse> getAllSubCategories() {
        return subcategoryRepository.getAllSubcategories();
    }

    @Override
    public List<SubcategoryResponse> getAllSubCategoryOrderByCategoryName(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new NotFoundException("Category with id : " + categoryId + " doesn't exist"));
        return subcategoryRepository.getAllSubCategoryOrderByCategoryName(category.getId());
    }

    @Override
    public SubCategoryResponseById getSubCategoriesById(Long id) {
        return subcategoryRepository.getSubCategoriesById(id).
                orElseThrow(() -> new NotFoundException("Subcategory with id : " + id + " doesn't exist"));
    }

    @Override
    public SimpleResponse deleteSubCategoriesBiId(Long id) {
        if (!subcategoryRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("SubCategory with id : " + id + " doesn't exist")
                    .build();
        }
        subcategoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("SubCategory with id : " + id + " successfully deleted ...!"))
                .build();
    }

    @Override
    public SimpleResponse updateSubCategory(Long id, SubCategory subCategory) {
        if (subcategoryRepository.existsByName(subCategory.getName())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("SubCategory with name: %s already exists!", subCategory.getName()))
                    .build();
        }

        SubCategory oldSubcategory = subcategoryRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User with id : " + id + " doesn't exist"));

        oldSubcategory.setName(subCategory.getName());
        subcategoryRepository.save(oldSubcategory);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Subcategory successfully updated ...!")
                .build();
    }

    @Override
    public Map<String, List<SubCategoryResponseGroupCategory>> groupingByCategories() {
        return subcategoryRepository.groupingByCategories().stream().collect(Collectors.groupingBy(SubCategoryResponseGroupCategory::categoryName));
    }


}
