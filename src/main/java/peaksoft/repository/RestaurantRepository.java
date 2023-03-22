package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.restaurantResponse.RestaurantResponse;
import peaksoft.dto.response.restaurantResponse.RestaurantResponseById;
import peaksoft.entity.Restaurant;

import java.util.List;
import java.util.Optional;

/**
 * Shabdanov Ilim
 **/
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Boolean existsByName(String name);

    @Query("select new peaksoft.dto.response.restaurantResponse.RestaurantResponse(r.id,r.name,r.location) from Restaurant r ")
    List<RestaurantResponse> getAllRestaurants();

    @Query("select new peaksoft.dto.response.restaurantResponse.RestaurantResponseById(r.id,r.name,r.location,r.restType,r.service) from Restaurant r where r.id = ?1")
    Optional<RestaurantResponseById> getRestaurantById(Long id);
}
