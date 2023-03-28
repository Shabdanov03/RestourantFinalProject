package peaksoft.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.stopListResponse.StopListResponse;
import peaksoft.entity.StopList;
import peaksoft.service.StopListService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@RestController
@RequestMapping("/api/stopLists")
public class StopListApi {
    private final StopListService stopListService;

    @Autowired
    public StopListApi(StopListService stopListService) {
        this.stopListService = stopListService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF')")
    @PostMapping
    public SimpleResponse saveStopList(@RequestBody @Valid StopListRequest stopListRequest) {
        return stopListService.saveStopList(stopListRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF','WALTER')")
    @GetMapping
    public List<StopListResponse> getAllStopLists() {
        return stopListService.getAllStopList();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF','WALTER')")
    @GetMapping("/{stopListId}")
    public StopListResponse getStopListById(@PathVariable Long stopListId) {
        return stopListService.getStopListById(stopListId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF')")
    @DeleteMapping("/{stopListId}")
    public SimpleResponse deleteStopListById(@PathVariable Long stopListId) {
        return stopListService.deleteStopListById(stopListId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF','WALTER')")
    @PutMapping("/{stopListId}")
    public SimpleResponse updateStopList(@RequestBody StopListRequest stopList, @PathVariable Long stopListId) {
        return stopListService.updateStopList(stopListId,stopList);
    }
}
