package peaksoft.service;

import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.restaurantResponse.RestaurantResponse;
import peaksoft.dto.response.restaurantResponse.RestaurantResponseById;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Restaurant;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface RestaurantService {

    SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest);

    List<RestaurantResponse> getAllRestaurants();

    RestaurantResponseById getRestaurantById(Long id);

    SimpleResponse deleteById(Long id);
    SimpleResponse updateRestaurant(Long id,Restaurant restaurant);


}
