package peaksoft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeRestaurantRequest;
import peaksoft.dto.request.UserWaiterRequestByCheque;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.chequeResponse.ChequeResponse;
import peaksoft.dto.response.chequeResponse.ChequeTotalByWaiterResponse;
import peaksoft.dto.response.chequeResponse.ChequeTotalResponse;
import peaksoft.service.ChequeService;

/**
 * Shabdanov Ilim
 **/
@RestController
@RequestMapping("/api/cheques")
public class ChequeApi {
    private final ChequeService chequeService;

    @Autowired
    public ChequeApi(ChequeService chequeService) {
        this.chequeService = chequeService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','CHIEF','WALTER')")
    @PostMapping
    public SimpleResponse saveCheque(@RequestBody ChequeRequest chequeRequest) {
        return chequeService.saveCheque(chequeRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/waiter")
    public ChequeTotalByWaiterResponse chequeTotalByWaiterResponse(@RequestBody UserWaiterRequestByCheque waiterRequestByCheque) {
        return chequeService.chequeTotalByWaiterResponse(waiterRequestByCheque);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/total")
    public ChequeTotalResponse restaurantTotalCheck(@RequestBody ChequeRestaurantRequest chequeRestaurantRequest) {
        return chequeService.restaurantTotalCheck(chequeRestaurantRequest);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WALTER')")
    @GetMapping("/{chequeId}")
    public ChequeResponse getChequeById(@PathVariable Long chequeId) {
        return chequeService.getChequeById(chequeId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WALTER')")
    @DeleteMapping("/{chequeId}")
    public SimpleResponse deleteChequeById(@PathVariable Long chequeId) {
        return chequeService.deleteChequeById(chequeId);
    }
}
