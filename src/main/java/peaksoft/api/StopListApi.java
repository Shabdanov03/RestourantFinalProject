package peaksoft.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RolesAllowed({"ROLE_ADMIN","ROLE_CHEF"})
    @PostMapping
    private SimpleResponse saveStopList(@RequestBody @Valid StopListRequest stopListRequest) {
        return stopListService.saveStopList(stopListRequest);
    }

    @RolesAllowed({"ROLE_ADMIN","ROLE_CHEF","ROLE_WALTER"})
    @GetMapping
    private List<StopListResponse> getAllStopLists() {
        return stopListService.getAllStopList();
    }

    @RolesAllowed({"ROLE_ADMIN","ROLE_CHEF","ROLE_WALTER"})
    @GetMapping("/{stopListId}")
    public StopListResponse getStopListById(@PathVariable Long stopListId) {
        return stopListService.getStopListById(stopListId);
    }

    @RolesAllowed({"ROLE_ADMIN","ROLE_CHEF"})
    @DeleteMapping("/{stopListId}")
    public SimpleResponse deleteStopListById(@PathVariable Long stopListId) {
        return stopListService.deleteStopListById(stopListId);
    }

    @RolesAllowed({"ROLE_ADMIN","ROLE_CHEF","ROLE_WALTER"})
    @PutMapping("/{stopListId}")
    public SimpleResponse updateStopList(@RequestBody StopList stopList, @PathVariable Long stopListId) {
        return stopListService.updateStopList(stopListId,stopList);
    }
}
