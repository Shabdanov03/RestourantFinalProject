package peaksoft.service;

import peaksoft.dto.request.StopListRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.stopListResponse.StopListResponse;
import peaksoft.entity.StopList;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface StopListService {
    SimpleResponse saveStopList(StopListRequest stopListRequest);
    List<StopListResponse> getAllStopList();
    StopListResponse getStopListById(Long id);
    SimpleResponse updateStopList(Long id, StopListRequest stopList);
    SimpleResponse deleteStopListById(Long id);

}
