package peaksoft.service;

import peaksoft.dto.request.AuthRequest;
import peaksoft.dto.request.UserAcceptRequest;
import peaksoft.dto.request.UserRequest;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.dto.response.userResponse.UserAcceptResponse;
import peaksoft.dto.response.userResponse.UserResponse;
import peaksoft.dto.response.UserTokenResponse;
import peaksoft.dto.response.userResponse.UserResponseById;
import peaksoft.entity.User;

import java.util.List;

/**
 * Shabdanov Ilim
 **/
public interface UserService {
    UserTokenResponse authenticate(AuthRequest authRequest);
    SimpleResponse saveUser(UserRequest userRequest);
    List<UserResponse> getAllUsers();
    UserResponseById getUserById(Long id);
    SimpleResponse deleteUserById(Long id);
    SimpleResponse updateUser(Long id, UserRequest user);
    SimpleResponse apply(UserAcceptRequest userAcceptRequest);
    UserAcceptResponse acceptApplication(Long id,boolean except);

}
