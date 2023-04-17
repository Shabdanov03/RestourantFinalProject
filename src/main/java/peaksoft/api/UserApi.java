package peaksoft.api;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import peaksoft.service.impl.UserServiceImpl;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
@RestController
@RequestMapping("/api/users")
public class UserApi {

    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserApi(UserService userService, UserServiceImpl userServiceImpl) {
        this.userService = userService;
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/login")
    public UserTokenResponse login(@RequestBody @Valid AuthRequest authRequest) {
        return userService.authenticate(authRequest);
    }



    @Operation(summary = "Suthorization with google", description = "You can register by google account")
    @PostMapping("/auth-google")
    public UserTokenResponse authWithGoogle(String tokenId) throws FirebaseAuthException {
        return userServiceImpl.authWithGoogle(tokenId);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public SimpleResponse saveUser(@RequestBody @Valid UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{userId}")
    public UserResponseById getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{userId}")
    public SimpleResponse deleteUserById(@PathVariable Long userId) {
        return userService.deleteUserById(userId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{userId}")
    public SimpleResponse updateUser(@RequestBody @Valid UserRequest user, @PathVariable Long userId) {
        return userService.updateUser(userId, user);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/apply")
    public SimpleResponse apply(@RequestBody @Valid UserAcceptRequest userAcceptRequest) {
        return userService.apply(userAcceptRequest);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/accept")
    public UserAcceptResponse acceptApplication(@RequestParam(required = false) Long id, @RequestParam(required = false) boolean except) {
        return userService.acceptApplication(id, except);
    }


}
