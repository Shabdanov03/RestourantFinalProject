package peaksoft.dto.response.chequeResponse;

import lombok.Builder;
import peaksoft.dto.response.menuItemResponse.MenuItemResponse;
import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Builder
public record ChequeResponse(

        String waiterFullName,
        List<MenuItemResponse> menuItems,
        int averagePrice,
        int service,
        int grandTotal
) {
}
