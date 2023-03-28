package peaksoft.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.config.jwt.JwtUtil;
import peaksoft.dto.request.AuthRequest;
import peaksoft.dto.request.UserAcceptRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.userResponse.UserAcceptResponse;
import peaksoft.dto.response.userResponse.UserResponse;
import peaksoft.dto.response.UserTokenResponse;
import peaksoft.dto.response.userResponse.UserResponseById;
import peaksoft.entity.Restaurant;
import peaksoft.entity.User;
import peaksoft.enums.Role;
import peaksoft.exception.NotFoundException;
import peaksoft.repository.RestaurantRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


/**
 * Shabdanov Ilim
 **/
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RestaurantRepository restaurantRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostConstruct
    public void init() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRole(Role.ADMIN);
        if (!userRepository.existsByEmail(user.getEmail())) {
            userRepository.save(user);
        }
    }

    @Override
    public UserTokenResponse authenticate(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.email(),
                        authRequest.password()
                )
        );
        User user = userRepository.findByEmail(authRequest.email())
                .orElseThrow(() -> new NotFoundException(String.format
                        ("User with email: %s doesn't exists", authRequest.email())));

        String token = jwtUtil.generateToken(user);
        System.out.println(token);
        return UserTokenResponse.builder()
                .token(token)
                .email(user.getEmail())
                .build();
    }

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        Restaurant restaurant = restaurantRepository.findById(userRequest.restaurantId())
                .orElseThrow(() -> new NotFoundException("Restaurant with id: " + userRequest.restaurantId() + " not found"));
        if (restaurant.getUser().size() > 15) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("There are no seats in the restaurant..!")
                    .build();
        }

        if (userRepository.existsByEmail(userRequest.email())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("User with email: %s already exists!", userRequest.email()))
                    .build();
        }

        if (userRequest.role().equals(Role.CHIEF)) {
            Period period = Period.between(userRequest.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 25 || period.getYears() > 45) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("For the vacancy of a cook, the age range is from 25 to 45 years!")
                        .build();
            }
            if (userRequest.experience() <= 1) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Cooking experience must be at least 2 years!")
                        .build();
            }
        } else if (userRequest.role().equals(Role.WALTER)) {
            Period period = Period.between(userRequest.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 18 || 30 < period.getYears()) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("For the vacancy of a waiter, the age range is from 18 to 30 years!")
                        .build();
            }
            if (userRequest.experience() <= 1) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Experience as a waiter must be at least 1 year!")
                        .build();
            }
        }

        User user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .dateOfBirth(userRequest.dateOfBirth())
                .email(userRequest.email())
                .password(passwordEncoder.encode(userRequest.password()))
                .phoneNumber(userRequest.phoneNumber())
                .role(userRequest.role())
                .experience(userRequest.experience())
                .restaurant(restaurant)
                .excepted(true)
                .build();

        userRepository.save(user);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User successfully saved in restaurant!")
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserResponseById getUserById(Long id) {
        return userRepository.getUserById(id).
                orElseThrow(() -> new NotFoundException("User with id : " + id + " doesn't exist"));
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .message("User with id : " + id + " doesn't exist")
                    .build();

        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id : " + id + " doesn't exist"));

        user.getCheques().forEach(x -> x.getMenuItems().forEach(menuItem -> menuItem.setCheques(null)));

        userRepository.delete(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with id : " + id + " successfully deleted ...!"))
                .build();
    }

    @Override
    public SimpleResponse updateUser(Long id, UserRequest user) {
        User oldUser = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException("User with id : " + id + " doesn't exist"));
        if (userRepository.existsByEmail(user.email())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("User with email: %s already exists!", user.email()))
                    .build();
        }

        if (user.role().equals(Role.CHIEF)) {
            Period period = Period.between(user.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 25 || period.getYears() > 45) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("For the vacancy of a cook, the age range is from 25 to 45 years!")
                        .build();
            }
            if (user.experience() <= 1) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Cooking experience must be at least 2 years!")
                        .build();
            }
        } else if (user.role().equals(Role.WALTER)) {
            Period period = Period.between(user.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 18 || 30 < period.getYears()) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("For the vacancy of a waiter, the age range is from 18 to 30 years!")
                        .build();
            }
            if (user.experience() <= 1) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Experience as a waiter must be at least 1 year!")
                        .build();
            }
        }
        oldUser.setFirstName(user.firstName());
        oldUser.setLastName(user.lastName());
        oldUser.setDateOfBirth(user.dateOfBirth());
        oldUser.setEmail(user.email());
        oldUser.setPassword(passwordEncoder.encode(user.password()));
        oldUser.setPhoneNumber(user.phoneNumber());
        oldUser.setRole(user.role());
        oldUser.setExperience(user.experience());
        userRepository.save(oldUser);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User successfully updated ...!")
                .build();
    }

    @Override
    public SimpleResponse apply(UserAcceptRequest userAcceptRequest) {
        Restaurant restaurant = restaurantRepository.findById(1L)
                .orElseThrow(() -> new NotFoundException("Restaurant with id: " + 1 + " not found"));
        if (restaurant.getUser().size() > 15) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("There are no seats in the restaurant..!")
                    .build();
        }
        if (userAcceptRequest.role().equals(Role.ADMIN)) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.FORBIDDEN)
                    .message("There is no vacancy for the administrator!")
                    .build();
        }

        if (userRepository.existsByEmail(userAcceptRequest.email())) {
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.CONFLICT)
                    .message(String.format("User with email: %s already exists!", userAcceptRequest.email()))
                    .build();
        }

        if (userAcceptRequest.role().equals(Role.CHIEF)) {
            Period period = Period.between(userAcceptRequest.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 25 || period.getYears() > 45) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("For the vacancy of a cook, the age range is from 25 to 45 years!")
                        .build();
            }
            if (userAcceptRequest.experience() <= 1) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Cooking experience must be at least 2 years!")
                        .build();
            }
        } else if (userAcceptRequest.role().equals(Role.WALTER)) {
            Period period = Period.between(userAcceptRequest.dateOfBirth(), LocalDate.now());
            if (period.getYears() < 18 || 30 < period.getYears()) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("For the vacancy of a waiter, the age range is from 18 to 30 years!")
                        .build();
            }
            if (userAcceptRequest.experience() <= 1) {
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Experience as a waiter must be at least 1 year!")
                        .build();
            }
        }
        User user = User.builder()
                .firstName(userAcceptRequest.firstName())
                .lastName(userAcceptRequest.lastName())
                .dateOfBirth(userAcceptRequest.dateOfBirth())
                .email(userAcceptRequest.email())
                .password(passwordEncoder.encode(userAcceptRequest.password()))
                .phoneNumber(userAcceptRequest.phoneNumber())
                .role(userAcceptRequest.role())
                .experience(userAcceptRequest.experience())
                .excepted(false)
                .build();
        userRepository.save(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with name : %s successfully saved !", user.getFirstName()))
                .build();
    }

    @Override
    public UserAcceptResponse acceptApplication(Long id, boolean except) {
        if (id == null) {
            return UserAcceptResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("No accepted..!")
                    .users(userRepository.findAllUsersByExceptIsFalse())
                    .build();
        }
        if (except) {
            User user = userRepository.findById(id).
                    orElseThrow(() -> new NotFoundException("User with id : " + id + " doesn't exist"));

            user.setRestaurant(restaurantRepository.findById(1L).
                    orElseThrow(() -> new NotFoundException("Restaurant with id:" + 1 + " not found..!")));
            user.setExcepted(true);
            return UserAcceptResponse.builder()
                    .httpStatus(HttpStatus.ACCEPTED)
                    .message(String.format("user with id : %s successfully accepted", id))
                    .users(List.of(userRepository.getUserById(id).
                            orElseThrow(() -> new NotFoundException("User with id:" + id + " not found..!"))))
                    .build();
        } else {
            userRepository.deleteById(id);
            return UserAcceptResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message(String.format("user with id : %s not accepted..!", id))
                    .build();
        }
    }


}
