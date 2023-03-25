package peaksoft.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemGlobalSearchResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemResponseById;
import peaksoft.entity.MenuItem;
import peaksoft.service.MenuItemService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@RestController
@RequestMapping("/api/menuItems")
public class MenuItemApi {
    private final MenuItemService menuItemService;

    @Autowired
    public MenuItemApi(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_CHEF"})
    @PostMapping
    private SimpleResponse saveMenuItem(@RequestBody @Valid MenuItemRequest menuItemRequest) {
        return menuItemService.saveMenuItem(menuItemRequest);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_CHEF", "ROLE_WALTER"})
    @GetMapping
    private List<MenuItemResponse> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_CHEF"})
    @GetMapping("/{menuItemId}")
    public MenuItemResponseById getMenuItemById(@PathVariable Long menuItemId) {
        return menuItemService.getMenuItemById(menuItemId);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_CHEF"})
    @DeleteMapping("/{menuItemId}")
    public SimpleResponse deleteMenuitemById(@PathVariable Long menuItemId) {
        return menuItemService.deleteMenuItemById(menuItemId);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_CHEF"})
    @PutMapping("/{menuItemId}")
    public SimpleResponse updateMenuItem(@RequestBody MenuItem menuItem, @PathVariable Long menuItemId) {
        return menuItemService.updateMenuItem(menuItemId, menuItem);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/search")
    public List<MenuItemGlobalSearchResponse> globalSearchMenuItems(@RequestParam(required = false) String word) {
        return menuItemService.globalSearchMenuItems(word);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/sort")
    public List<MenuItemResponse> sortByMenuItemPriceAscOrDesc(@RequestParam(required = false, defaultValue = "asc") String ascOrDesc) {
        return menuItemService.sortByMenuItemPriceAscOrDesc(ascOrDesc);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/filter")
    public List<MenuItemResponse> filterMenuItemsByIsVegetarian(@RequestParam(required = false, defaultValue = "false") boolean isVegan) {
        return menuItemService.filterMenuItemsByIsVegetarian(isVegan);
    }

    @GetMapping("/pagination")
    public PaginationResponse pagination(@RequestParam int page, @RequestParam int size) {
        return menuItemService.getMenuItemPagination(page, size);
    }

}
