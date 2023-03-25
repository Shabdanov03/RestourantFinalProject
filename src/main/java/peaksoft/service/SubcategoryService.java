package peaksoft.service;

import peaksoft.dto.request.SubCategoryRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.subCategoryResponse.SubCategoryResponseById;
import peaksoft.dto.response.subCategoryResponse.SubCategoryResponseGroupCategory;
import peaksoft.dto.response.subCategoryResponse.SubcategoryResponse;
import peaksoft.entity.SubCategory;

import java.util.List;
import java.util.Map;

/**
 * Shabdanov Ilim
 **/
public interface SubcategoryService {
    SimpleResponse saveSubcategory(SubCategoryRequest subCategoryRequest);
    List<SubcategoryResponse> getAllSubCategories();
    List<SubcategoryResponse> getAllSubCategoryOrderByCategoryName(Long categoryId);
    SubCategoryResponseById getSubCategoriesById(Long id);
    SimpleResponse deleteSubCategoriesBiId(Long id);
    SimpleResponse updateSubCategory(Long id, SubCategory subCategory);
    Map<String,List<SubCategoryResponseGroupCategory>> groupingByCategories();

}
