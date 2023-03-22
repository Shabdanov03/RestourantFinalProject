package peaksoft.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.AuthRequest;
import peaksoft.dto.request.UserAcceptRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.userResponse.UserAcceptResponse;
import peaksoft.dto.response.userResponse.UserResponse;
import peaksoft.dto.response.UserTokenResponse;
import peaksoft.dto.response.userResponse.UserResponseById;
import peaksoft.service.UserService;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@RestController
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;

    @Autowired
    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    private UserTokenResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return userService.authenticate(authRequest);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    private SimpleResponse saveUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping
    private List<UserResponse> getAllUsers(){
        return userService.getAllUsers();
    }

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/{userId}")
    public UserResponseById getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{userId}")
    public SimpleResponse deleteUserById(@PathVariable Long userId){
        return userService.deleteUserById(userId);
    }

    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{userId}")
    public SimpleResponse updateUser(@RequestBody @Valid UserRequest user, @PathVariable Long userId){
        return userService.updateUser(userId,user);
    }
    @PostMapping("/apply")
    public SimpleResponse apply(@RequestBody @Valid UserAcceptRequest userAcceptRequest){
        return userService.apply(userAcceptRequest);
    }
    @RolesAllowed("ROLE_ADMIN")
    @GetMapping("/accept")
    public UserAcceptResponse acceptApplication(@RequestParam(required = false) Long id,@RequestParam(required = false) boolean except){
        return userService.acceptApplication(id,except);
    }

}
