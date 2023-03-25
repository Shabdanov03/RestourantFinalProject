package peaksoft.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemGlobalSearchResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemResponseById;
import peaksoft.dto.response.userResponse.UserResponse;
import peaksoft.dto.response.userResponse.UserResponseById;
import peaksoft.entity.MenuItem;
import peaksoft.entity.User;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface MenuItemService {

    SimpleResponse saveMenuItem(MenuItemRequest menuItemRequest);
    List<MenuItemResponse> getAllMenuItems();
    MenuItemResponseById getMenuItemById(Long id);
    SimpleResponse deleteMenuItemById(Long id);
    SimpleResponse updateMenuItem(Long id, MenuItem menuItem);
    List<MenuItemGlobalSearchResponse> globalSearchMenuItems(String word);

    List<MenuItemResponse> sortByMenuItemPriceAscOrDesc(String ascOrDesc);

    List<MenuItemResponse> filterMenuItemsByIsVegetarian(boolean isVegan);

    PaginationResponse getMenuItemPagination(int page, int size);

}
