package peaksoft.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.restaurantResponse.RestaurantResponse;
import peaksoft.dto.response.restaurantResponse.RestaurantResponseById;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.service.RestaurantService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantApi {
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantApi(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveRestaurant(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        return restaurantService.saveRestaurant(restaurantRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<RestaurantResponse> getAllRestaurant() {
        return restaurantService.getAllRestaurants();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{restaurantId}")
    public RestaurantResponseById getRestaurantById(@PathVariable Long restaurantId) {
        return restaurantService.getRestaurantById(restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{restaurantId}")
    public SimpleResponse deleteRestaurantById(@PathVariable Long restaurantId) {
        return restaurantService.deleteById(restaurantId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{restaurantId}")
    public SimpleResponse updateRestaurant(@RequestBody Restaurant restaurant, @PathVariable Long restaurantId) {
        return restaurantService.updateRestaurant(restaurantId, restaurant);
    }
}
