package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.menuItemResponse.MenuItemGlobalSearchResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemResponseById;
import peaksoft.entity.MenuItem;

import java.util.List;
import java.util.Optional;

/**
 * Shabdanov Ilim
 **/

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("select new peaksoft.dto.response.menuItemResponse.MenuItemResponse(m.id,m.name,m.image,m.price,m.isVegetarian)from MenuItem m join m.stopList s where s.date != current date   ")
    List<MenuItemResponse> getAllMenuItems();
    List<MenuItemResponse> getAllByStopListNull();
    @Query("select new peaksoft.dto.response.menuItemResponse.MenuItemResponseById(m.id,m.name,m.image,m.price,m.description,m.isVegetarian,m.restaurant.id) from MenuItem m where m.id=?1")
    Optional<MenuItemResponseById> getMenuItemById(Long id);

    @Query("select  new peaksoft.dto.response.menuItemResponse.MenuItemGlobalSearchResponse(c.name,s.name,m.name,m.price,m.isVegetarian)" +
            " from MenuItem m join m.subCategory s join m.subCategory.category c where  c.name ilike :word or s.name ilike :word or m.name ilike :word")
    List<MenuItemGlobalSearchResponse> globalSearchMenuItems(String word);

    @Query("select new peaksoft.dto.response.menuItemResponse.MenuItemResponse(m.id,m.name,m.image,m.price,m.isVegetarian) from MenuItem m  order by m.price asc ")
    List<MenuItemResponse> sortByMenuItemPriceAsc();

    @Query("select new peaksoft.dto.response.menuItemResponse.MenuItemResponse(m.id,m.name,m.image,m.price,m.isVegetarian) from MenuItem m  order by m.price desc ")
    List<MenuItemResponse> sortByMenuItemPriceDesc();

    @Query("select new peaksoft.dto.response.menuItemResponse.MenuItemResponse(m.id,m.name,m.image,m.price,m.isVegetarian) from MenuItem m where (m.isVegetarian = :isVegan or :isVegan is null ) ")
    List<MenuItemResponse> filterMenuItemsByIsVegetarian(boolean isVegan);

    @Query("select new peaksoft.dto.response.menuItemResponse.MenuItemResponse(m.id,m.name,m.image,m.price,m.isVegetarian) from MenuItem m")
    Page<MenuItemResponse> getAllPageable(Pageable pageable);

}
