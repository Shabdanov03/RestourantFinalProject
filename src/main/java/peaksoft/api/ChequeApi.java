package peaksoft.api;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RolesAllowed({"ROLE_ADMIN", "ROLE_WALTER"})
    @PostMapping
    public SimpleResponse saveCheque(@RequestBody ChequeRequest chequeRequest) {
        return chequeService.saveCheque(chequeRequest);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/waiter")
    public ChequeTotalByWaiterResponse chequeTotalByWaiterResponse(@RequestBody UserWaiterRequestByCheque waiterRequestByCheque) {
        return chequeService.chequeTotalByWaiterResponse(waiterRequestByCheque);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/total")
    public ChequeTotalResponse restaurantTotalCheck(@RequestBody ChequeRestaurantRequest chequeRestaurantRequest) {
        return chequeService.restaurantTotalCheck(chequeRestaurantRequest);
    }

    @RolesAllowed({"ROLE_ADMIN","ROLE_WALTER"})
    @GetMapping("/{chequeId}")
    public ChequeResponse getChequeById(@PathVariable Long chequeId) {
        return chequeService.getChequeById(chequeId);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_WALTER"})
    @DeleteMapping("/{chequeId}")
    public SimpleResponse deleteChequeById(@PathVariable Long chequeId) {
        return chequeService.deleteChequeById(chequeId);
    }

//    @RolesAllowed({"ROLE_ADMIN", "ROLE_WALTER"})
//    @PutMapping("/{id}")
//    SimpleResponse updateCheque(@RequestBody ChequeRequest chequeRequest,@PathVariable Long id){
//        return chequeService.updateCheque(id,chequeRequest);
//    }

}
