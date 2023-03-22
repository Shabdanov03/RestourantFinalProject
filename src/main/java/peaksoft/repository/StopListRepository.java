package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.stopListResponse.StopListResponse;
import peaksoft.entity.StopList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Shabdanov Ilim
 **/
public interface StopListRepository extends JpaRepository<StopList, Long> {

    boolean existsByDateAndMenuItem_NameAndId(LocalDate date,String name,Long id);
    boolean existsByDateAndMenuItem_Name(LocalDate date,String name);
    @Query("select new peaksoft.dto.response.stopListResponse.StopListResponse(s.id,s.reason,s.date,s.menuItem.name) from StopList s")
    List<StopListResponse> getAllStopList();

    @Query("select new peaksoft.dto.response.stopListResponse.StopListResponse(s.id,s.reason,s.date,s.menuItem.name) from StopList s where s.id = ?1")
    Optional<StopListResponse> getStopListById(Long id);

}
