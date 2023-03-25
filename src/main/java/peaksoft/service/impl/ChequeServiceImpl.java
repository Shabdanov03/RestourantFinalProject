package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.dto.request.ChequeRequest;
import peaksoft.dto.request.ChequeRestaurantRequest;
import peaksoft.dto.request.UserWaiterRequestByCheque;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.chequeResponse.ChequeResponse;
import peaksoft.dto.response.chequeResponse.ChequeTotalByWaiterResponse;
import peaksoft.dto.response.chequeResponse.ChequeTotalResponse;
import peaksoft.entity.Cheque;
import peaksoft.entity.MenuItem;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.ChequeRepository;
import peaksoft.repository.MenuItemRepository;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

/**
 * Shabdanov Ilim
 **/
@Service
@Transactional
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public ChequeServiceImpl(ChequeRepository chequeRepository, UserRepository userRepository, MenuItemRepository menuItemRepository,
                             RestaurantRepository restaurantRepository) {
        this.chequeRepository = chequeRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public SimpleResponse saveCheque(ChequeRequest chequeRequest) {
        int priceAVG = 0;
        User user = userRepository.findById(chequeRequest.userId()).orElseThrow(() ->
                new NotFoundException("User with id : " + chequeRequest.userId() + " doesn't exist"));

        List<MenuItem> menuItems = menuItemRepository.findAllById(chequeRequest.menuItemsIdes());
        for (MenuItem menuItem : menuItems) {
            priceAVG += menuItem.getPrice();
        }
        Cheque cheque = Cheque.builder()
                .priceAverage(priceAVG)
                .createdAt(LocalDate.now())
                .menuItems(menuItems)
                .user(user)
                .build();
        List<MenuItem> menuItem = menuItemRepository.findAllById(chequeRequest.menuItemsIdes());
        for (MenuItem menu : menuItem) {
            menu.addCheque(cheque);
            cheque.addMenuItems(menu);
        }
        chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Check completed successfully")
                .build();
    }

    @Override
    public ChequeTotalByWaiterResponse chequeTotalByWaiterResponse(UserWaiterRequestByCheque waiterRequestByCheque) {
        User user = userRepository.findById(waiterRequestByCheque.userId())
                .orElseThrow(() ->
                        new NotFoundException("User with id : " + waiterRequestByCheque.userId() + " doesn't exist"));
        int allSum = 0;
        int count = 0;
        if (user.getRole().equals(Role.WALTER)) {
            for (Cheque cheque : user.getCheques()) {
                if (cheque.getCreatedAt().equals(waiterRequestByCheque.date())) {
                    int priceService = cheque.getPriceAverage() * user.getRestaurant().getService() / 100;
                    allSum = priceService + cheque.getPriceAverage();
                    count++;
                }
            }
        }
        return ChequeTotalByWaiterResponse.builder()
                .date(waiterRequestByCheque.date())
                .waiterFulName(user.getFirstName() + "  " + user.getLastName())
                .chequeSum(allSum)
                .countCheque(count)
                .build();
    }

    @Override
    public ChequeTotalResponse restaurantTotalCheck(ChequeRestaurantRequest chequeRestaurantRequest) {
        Restaurant restaurant = restaurantRepository.findById(chequeRestaurantRequest.restaurantId())
                .orElseThrow(() ->
                        new NotFoundException("Restaurant with id : " + chequeRestaurantRequest.restaurantId() + " doesn't exist"));

        int averagePrice = 0;
        int checkCount = 0;
        for (User user : restaurant.getUser()) {
            if (user.getRole().equals(Role.WALTER)) {
                for (Cheque cheque : user.getCheques()) {
                    if (cheque.getCreatedAt().equals(chequeRestaurantRequest.date())) {
                        averagePrice += cheque.getPriceAverage();
                    }
                    checkCount++;
                }
            }
        }
        int priceService = averagePrice * restaurant.getService() / 100;

        return ChequeTotalResponse.builder()
                .restaurantName("=========" + restaurant.getName() + "==========")
                .date(chequeRestaurantRequest.date())
                .averagePrice(averagePrice)
                .service(restaurant.getService())
                .grandTotal(averagePrice + priceService)
                .grandTotalAVG((averagePrice + priceService) / checkCount)
                .build();
    }

    @Override
    public ChequeResponse getChequeById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException(String.format("Restaurant with id: %s not found", id)));


        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Cheque with id: %s not found", id)));
        User user = cheque.getUser();
        int sum = cheque.getMenuItems().stream().mapToInt(MenuItem::getPrice).sum();
        cheque.setPriceAverage(sum);
        chequeRepository.save(cheque);

        int total = cheque.getPriceAverage() + cheque.getPriceAverage() * user.getRestaurant().getService() / 100;

        return ChequeResponse.builder()
                .waiterFullName(user.getLastName() + " " + user.getFirstName())
                .menuItems(menuItemRepository.getAllMenuItems())
                .averagePrice(cheque.getPriceAverage())
                .service(restaurant.getService())
                .grandTotal(total)
                .build();

    }

    @Override
    public SimpleResponse deleteChequeById(Long id) {
        if (!chequeRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message(String.format("Cheque with id: %s not found", id))
                    .build();
        }

        Cheque cheque = chequeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Cheque with id: %s not found", id)));
        for (int i = 0; i < cheque.getMenuItems().size(); i++) {
            cheque.getMenuItems().get(i).getCheques().remove(cheque);
        }
        List<Cheque> chequeList = cheque.getUser().getCheques();
        chequeList.removeIf(x->x.getId().equals(id));

        chequeRepository.deleteById(id);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Cheque with id: %s successfully deleted", id))
                .build();
    }

    @Override
    public SimpleResponse updateCheque(Long id, ChequeRequest chequeRequest) {
        int priceAVG = 0;
        Cheque oldCheque = chequeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Cheque with id : " + id + " doesn't exist"));

        User user = userRepository.findById(chequeRequest.userId()).orElseThrow(() ->
                new NotFoundException("User with id : " + chequeRequest.userId() + " doesn't exist"));

        List<MenuItem> menuItems = menuItemRepository.findAllById(chequeRequest.menuItemsIdes());
        for (MenuItem menuItem : menuItems) {
            priceAVG += menuItem.getPrice();
        }

        oldCheque.setPriceAverage(priceAVG);
        oldCheque.setUser(user);
        oldCheque.setCreatedAt(LocalDate.now());
        oldCheque.setMenuItems(menuItems);

        List<MenuItem> menuItem = menuItemRepository.findAllById(chequeRequest.menuItemsIdes());
        for (MenuItem menu : menuItem) {
            menu.addCheque(oldCheque);
            oldCheque.addMenuItems(menu);
        }
        chequeRepository.save(oldCheque);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Check successfully updated...!")
                .build();
    }


}
