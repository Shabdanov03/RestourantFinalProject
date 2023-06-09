package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.MenuItemRequest;
import peaksoft.dto.response.PaginationResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemGlobalSearchResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemResponse;
import peaksoft.dto.response.menuItemResponse.MenuItemResponseById;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.SubCategory;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.SubcategoryRepository;
import peaksoft.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Autowired
    public MenuItemServiceImpl(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository,
                               SubcategoryRepository subcategoryRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.subcategoryRepository = subcategoryRepository;
    }

    @Override
    public SimpleResponse saveMenuItem(MenuItemRequest menuItemRequest) {

        Restaurant restaurant = restaurantRepository.findById(menuItemRequest.restaurantId())
                .orElseThrow(() -> new NotFoundException("Restaurant with id: " + menuItemRequest.restaurantId() + " not found"));

        SubCategory subCategory = subcategoryRepository.findById(menuItemRequest.subCategoryId())
                .orElseThrow(() -> new NotFoundException("SubCategory with id: " + menuItemRequest.restaurantId() + " not found"));

        if (menuItemRepository.existsByName(menuItemRequest.name())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("MenuItem with name : %s already exists",
                            menuItemRequest.name()))
                    .build();
        }

        if (menuItemRequest.price() <= 0){
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Price not can't be negative!")
                    .build();
        }

        MenuItem menuItem = MenuItem.builder()
                .name(menuItemRequest.name())
                .image(menuItemRequest.image())
                .price(menuItemRequest.price())
                .description(menuItemRequest.description())
                .isVegetarian(menuItemRequest.isVegetarian())
                .restaurant(restaurant)
                .subCategory(subCategory)
                .build();
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("MenuItem successfully saved in restaurant!")
                .build();
    }

    @Override
    public List<MenuItemResponse> getAllMenuItems() {
        List<MenuItemResponse> list = new ArrayList<>();
        list.addAll(menuItemRepository.getAllMenuItems());
        list.addAll(menuItemRepository.getAllByStopListNull());
        return list;
    }

    @Override
    public MenuItemResponseById getMenuItemById(Long id) {
        return menuItemRepository.getMenuItemById(id)
                .orElseThrow(() -> new NotFoundException("MenuItem with id : " + id + " doesn't exist"));
    }

    @Override
    public SimpleResponse deleteMenuItemById(Long id) {
        if (!menuItemRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("MenuItem with id : " + id + " doesn't exist")
                    .build();
        }
        menuItemRepository.deleteById(id);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("MenuItem with id : " + id + " successfully deleted ...!"))
                .build();
    }

    @Override
    public SimpleResponse updateMenuItem(Long id, MenuItem menuItem) {
        MenuItem oldMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MenuItem with id : " + id + " doesn't exist"));

        if (menuItem.getPrice() <= 0){
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("Price not can't be negative!")
                    .build();
        }

        oldMenuItem.setName(menuItem.getName());
        oldMenuItem.setImage(menuItem.getImage());
        oldMenuItem.setPrice(menuItem.getPrice());
        oldMenuItem.setDescription(menuItem.getDescription());
        oldMenuItem.setVegetarian(menuItem.isVegetarian());
        menuItemRepository.save(oldMenuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("MenuItem successfully updated ...!")
                .build();
    }

    @Override
    public List<MenuItemGlobalSearchResponse> globalSearchMenuItems(String word) {
            return menuItemRepository.globalSearchMenuItems("%"+word+"%");
    }

    @Override
    public List<MenuItemResponse> sortByMenuItemPriceAscOrDesc(String ascOrDesc) {
        if (ascOrDesc.equalsIgnoreCase("asc")){
            return menuItemRepository.sortByMenuItemPriceAsc();
        }
        if (ascOrDesc.equalsIgnoreCase("desc")){
            return menuItemRepository.sortByMenuItemPriceDesc();
        }
        else {
            return menuItemRepository.getAllMenuItems();
        }
    }


    @Override
    public List<MenuItemResponse> filterMenuItemsByIsVegetarian(boolean isVegan) {
        return menuItemRepository.filterMenuItemsByIsVegetarian(isVegan);
    }

    @Override
    public PaginationResponse getMenuItemPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page-1, size, Sort.by("price"));
        Page<MenuItemResponse> itemPage = menuItemRepository.getAllPageable(pageable);

        List<MenuItemResponse> list = itemPage.getContent().stream().map(m -> new MenuItemResponse(m.id(), m.name(), m.image(), m.price(), m.isVegetarian())).toList();
        PaginationResponse response = new PaginationResponse();
        response.setMenuItemResponses(list);
        response.setCurrentPage(itemPage.getNumber()+1);
        response.setPageSize(itemPage.getSize());
        return response;
    }
}
