package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.RestaurantRequest;
import peaksoft.dto.response.restaurantResponse.RestaurantResponse;
import peaksoft.dto.response.restaurantResponse.RestaurantResponseById;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Restaurant;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.service.RestaurantService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Override
    public SimpleResponse saveRestaurant(RestaurantRequest restaurantRequest) {
        if (restaurantRepository.existsByName(restaurantRequest.name())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("Restaurant with name : %s already exists",
                            restaurantRequest.name()))
                    .build();
        }
        Restaurant restaurant = Restaurant.builder()
                .name(restaurantRequest.name())
                .location(restaurantRequest.location())
                .restType(restaurantRequest.restType())
                .service(restaurantRequest.service())
                .build();
        restaurantRepository.save(restaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with name : %s successfully saved ...!",
                        restaurantRequest.name()))
                .build();
    }

    @Override
    public List<RestaurantResponse> getAllRestaurants() {
        return restaurantRepository.getAllRestaurants();
    }

    @Override
    public RestaurantResponseById getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id : " + id + " doesn't exist"));
        restaurant.setNumberOfEmployees(restaurant.getUser().size());
        restaurantRepository.save(restaurant);

        return restaurantRepository.getRestaurantById(id).
                orElseThrow(() -> new NotFoundException("Restaurant with id : " + id + " doesn't exist"));

    }

    @Override
    public SimpleResponse deleteById(Long id) {
        if (!restaurantRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("Restaurant with id : " + id + " doesn't exist")
                    .build();
        }
        restaurantRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Restaurant with id : "+id+" successfully deleted ...!"))
                .build();
    }

    @Override
    public SimpleResponse updateRestaurant(Long id, Restaurant restaurant) {
        Restaurant oldRestaurant = restaurantRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Restaurant with id : " + id + " doesn't exist"));

        oldRestaurant.setName(restaurant.getName());
        oldRestaurant.setLocation(restaurant.getLocation());
        oldRestaurant.setRestType(restaurant.getLocation());
        oldRestaurant.setService(restaurant.getService());
        restaurantRepository.save(oldRestaurant);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Restaurant successfully updated ...!")
                .build();
    }
}
