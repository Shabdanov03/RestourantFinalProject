package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.categoryResponse.CategoryResponse;
import peaksoft.dto.response.categoryResponse.CategoryResponseById;
import peaksoft.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * Shabdanov Ilim
 **/
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Boolean existsByName(String name);

    @Query("select new peaksoft.dto.response.categoryResponse.CategoryResponse(c.id,c.name) from Category c")
    List<CategoryResponse> getAllCategories();

    @Query("select new peaksoft.dto.response.categoryResponse.CategoryResponseById(c.id,c.name) from Category c where c.id =?1")
    Optional<CategoryResponseById> getCategoriesById(Long id);
}
