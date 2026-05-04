package in.theexplorers.vms.authservice.user.service;

import in.theexplorers.vms.authservice.user.dto.CreateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UpdateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    /**
     * Creates new user.
     */
    UserResponse createUser(CreateUserRequest user);

    Page<UserResponse> getAllUsers(int page, int size, String active);

//    Optional<User> findByUsername(String username);
//
//    Optional<User> findByEmail(String email);
    /**
     * Creates new user.
     */
    UserResponse getUserById(Long id);

    UserResponse updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);

    void updateStatus(Long id, boolean active);

}