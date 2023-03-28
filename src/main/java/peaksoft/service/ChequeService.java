package peaksoft.service;

import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeRestaurantRequest;
import peaksoft.dto.request.UserWaiterRequestByCheque;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.chequeResponse.ChequeResponse;
import peaksoft.dto.response.chequeResponse.ChequeTotalByWaiterResponse;
import peaksoft.dto.response.chequeResponse.ChequeTotalResponse;

/**
 * Shabdanov Ilim
 **/
public interface ChequeService {

    SimpleResponse saveCheque(ChequeRequest chequeRequest);
    ChequeTotalByWaiterResponse chequeTotalByWaiterResponse(UserWaiterRequestByCheque waiterRequestByCheque);

    ChequeTotalResponse restaurantTotalCheck(ChequeRestaurantRequest chequeRestaurantRequest);

    ChequeResponse getChequeById(Long id);
    SimpleResponse deleteChequeById(Long id);
}
