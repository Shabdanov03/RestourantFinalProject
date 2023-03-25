package peaksoft.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import peaksoft.dto.response.userResponse.UserResponse;
import peaksoft.dto.response.userResponse.UserResponseById;
import peaksoft.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Shabdanov Ilim
 **/
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("select new peaksoft.dto.response.userResponse.UserResponse(u.id,u.firstName,u.lastName,u.dateOfBirth) from User u")
    List<UserResponse> getAllUsers();

    @Query("select new peaksoft.dto.response.userResponse.UserResponseById(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.password,u.phoneNumber,u.role,u.experience) from User u where u.id = ?1")
    Optional<UserResponseById> getUserById(Long id);
    @Query("select new peaksoft.dto.response.userResponse.UserResponseById" +
            "(u.id,u.firstName,u.lastName,u.dateOfBirth,u.email,u.password,u.phoneNumber,u.role,u.experience) from User u where u.excepted = false ")
    List<UserResponseById> findAllUsersByExceptIsFalse();


}
