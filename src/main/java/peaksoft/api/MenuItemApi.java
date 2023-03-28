package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF')")
    @PostMapping
    public SimpleResponse saveMenuItem(@RequestBody @Valid MenuItemRequest menuItemRequest) {
        return menuItemService.saveMenuItem(menuItemRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF')")
    @GetMapping
    public List<MenuItemResponse> getAllMenuItems() {
        return menuItemService.getAllMenuItems();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF')")
    @GetMapping("/{menuItemId}")
    public MenuItemResponseById getMenuItemById(@PathVariable Long menuItemId) {
        return menuItemService.getMenuItemById(menuItemId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF')")
    @DeleteMapping("/{menuItemId}")
    public SimpleResponse deleteMenuitemById(@PathVariable Long menuItemId) {
        return menuItemService.deleteMenuItemById(menuItemId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF')")
    @PutMapping("/{menuItemId}")
    public SimpleResponse updateMenuItem(@RequestBody MenuItem menuItem, @PathVariable Long menuItemId) {
        return menuItemService.updateMenuItem(menuItemId, menuItem);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/search")
    public List<MenuItemGlobalSearchResponse> globalSearchMenuItems(@RequestParam(required = false) String word) {
        return menuItemService.globalSearchMenuItems(word);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/sort")
    public List<MenuItemResponse> sortByMenuItemPriceAscOrDesc(@RequestParam(required = false, defaultValue = "asc") String ascOrDesc) {
        return menuItemService.sortByMenuItemPriceAscOrDesc(ascOrDesc);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/filter")
    public List<MenuItemResponse> filterMenuItemsByIsVegetarian(@RequestParam(required = false, defaultValue = "false") boolean isVegan) {
        return menuItemService.filterMenuItemsByIsVegetarian(isVegan);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF','WALTER')")
    @GetMapping("/pagination")
    public PaginationResponse pagination(@RequestParam(required = false,defaultValue = "2" ) int page, @RequestParam(required = false,defaultValue = "2") int size) {
        return menuItemService.getMenuItemPagination(page, size);
    }

}
