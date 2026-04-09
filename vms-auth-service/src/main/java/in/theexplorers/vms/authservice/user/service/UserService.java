package in.theexplorers.vms.authservice.user.service;

import in.theexplorers.vms.authservice.user.dto.CreateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UserResponse;
import in.theexplorers.vms.authservice.user.entity.User;

import java.util.Optional;

public interface UserService {
    /**
     * Creates new user.
     */
    UserResponse createUser(CreateUserRequest user);

//    Optional<User> findByUsername(String username);
//
//    Optional<User> findByEmail(String email);
    /**
     * Creates new user.
     */
    UserResponse getById(Long id);

}