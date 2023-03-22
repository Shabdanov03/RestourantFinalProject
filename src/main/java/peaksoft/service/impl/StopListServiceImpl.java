package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.stopListResponse.StopListResponse;
import peaksoft.entity.MenuItem;
import peaksoft.entity.StopList;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.StopListRepository;
import peaksoft.service.StopListService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Transactional
@Service
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Autowired
    public StopListServiceImpl(StopListRepository stopListRepository, MenuItemRepository menuItemRepository) {
        this.stopListRepository = stopListRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @Override
    public SimpleResponse saveStopList(StopListRequest stopListRequest) {
        MenuItem menuItem = menuItemRepository.findById(stopListRequest.menuItemId())
                .orElseThrow(() -> new NotFoundException("MenuItem with id: " + stopListRequest.menuItemId() + " not found"));

        boolean exists = stopListRepository.existsByDateAndMenuItem_Name(stopListRequest.date(), menuItem.getName());
        if (exists) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("The MenuItem named %s has already been saved to the stop list on this date", menuItem.getName()))
                    .build();
        }

        StopList stopList = StopList.builder()
                .reason(stopListRequest.reason())
                .date(stopListRequest.date())
                .menuItem(menuItem)
                .build();
        stopListRepository.save(stopList);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("MenuItem named %s successfully saved to stop list", menuItem.getName()))
                .build();
    }

    @Override
    public List<StopListResponse> getAllStopList() {
        return stopListRepository.getAllStopList();
    }

    @Override
    public StopListResponse getStopListById(Long id) {
        return stopListRepository.getStopListById(id)
                .orElseThrow(() -> new NotFoundException("StopList with id : " + id + " doesn't exist"));

    }

    @Override
    public SimpleResponse updateStopList(Long id, StopList stopList) {
        StopList oldStopList = stopListRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("StopList with id : " + id + " doesn't exist"));
        boolean exists = stopListRepository.existsByDateAndMenuItem_NameAndId(oldStopList.getDate(), oldStopList.getMenuItem().getName(), oldStopList.getId());

        if (exists) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(String.format("The MenuItem with id %s has already been saved to the stop list on this date", stopList.getId()))
                    .build();
        }

        oldStopList.setReason(stopList.getReason());
        oldStopList.setDate(stopList.getDate());

        stopListRepository.save(oldStopList);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("MenuItem with id : %s successfully updated",id))
                .build();

    }

    @Override
    public SimpleResponse deleteStopListById(Long id) {
        if (!menuItemRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(String.format("StopList with id: %s doesn't exist", id))
                    .build();
        }
        stopListRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("StopList with id: %s successfully deleted", id))
                .build();
    }

}
