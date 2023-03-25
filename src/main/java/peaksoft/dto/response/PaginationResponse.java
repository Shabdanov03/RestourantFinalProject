package peaksoft.dto.response;

import lombok.*;
import peaksoft.dto.response.menuItemResponse.MenuItemResponse;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {
    private List<MenuItemResponse> menuItemResponses;
    private int currentPage;
    private int pageSize;

}
