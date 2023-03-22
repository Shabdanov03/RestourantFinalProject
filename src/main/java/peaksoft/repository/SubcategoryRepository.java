package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.subCategoryResponse.SubCategoryResponseById;
import peaksoft.dto.response.subCategoryResponse.SubCategoryResponseGroupCategory;
import peaksoft.dto.response.subCategoryResponse.SubcategoryResponse;
import peaksoft.entity.Category;
import peaksoft.entity.SubCategory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Shabdanov Ilim
 **/
public interface SubcategoryRepository extends JpaRepository<SubCategory,Long> {
    boolean existsByName(String name);
    @Query("select new peaksoft.dto.response.subCategoryResponse.SubcategoryResponse(s.id,s.name,s.category.id) from SubCategory s")
    List<SubcategoryResponse> getAllSubcategories();

    @Query("select new peaksoft.dto.response.subCategoryResponse.SubcategoryResponse(s.id,s.name,s.category.id) from SubCategory s where s.category.id = ?1 order by s.name")
    List<SubcategoryResponse> getAllSubCategoryOrderByCategoryName(Long subcategoryId);
    @Query("select new peaksoft.dto.response.subCategoryResponse.SubCategoryResponseById(s.id,s.name,s.category.id)from SubCategory s where s.id=?1")
    Optional<SubCategoryResponseById> getSubCategoriesById(Long id);
    @Query("select  new peaksoft.dto.response.subCategoryResponse.SubCategoryResponseGroupCategory(s.name,s.category.name) from SubCategory  s")
    List<SubCategoryResponseGroupCategory> groupingByCategories();
}
