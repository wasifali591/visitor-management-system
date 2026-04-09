package in.theexplorers.vms.authservice.user.service.impl;

import in.theexplorers.vms.authservice.common.exception.ConflictException;
import in.theexplorers.vms.authservice.common.exception.ResourceNotFoundException;
import in.theexplorers.vms.authservice.user.dto.CreateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UserResponse;
import in.theexplorers.vms.authservice.user.entity.User;
import in.theexplorers.vms.authservice.user.converter.UserConverter;
import in.theexplorers.vms.authservice.user.repository.UserRepository;
import in.theexplorers.vms.authservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserService.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    /**
     * Creates new user with validation.
     */
    @Override
    public UserResponse createUser(CreateUserRequest request) {

        log.info("Creating user with username: {}", request.getUsername());

        // Check duplicate username
        if (userRepository.existsByUsername(request.getUsername())) {
            log.warn("Username already exists: {}", request.getUsername());
            throw new ConflictException("Username already exists");
        }

        // Check duplicate email
        if (request.getEmail() != null &&
                userRepository.existsByEmail(request.getEmail())) {

            log.warn("Email already exists: {}", request.getEmail());
            throw new ConflictException("Email already exists");
        }

        // Convert DTO to entity using mapper
        User user = userConverter.toEntity(request);

        User savedUser = userRepository.save(user);

        log.info("User created successfully with id: {}", savedUser.getId());

        return userConverter.toResponse(savedUser);
    }

//    @Override
//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    @Override
//    public Optional<User> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    /**
     * Fetch user by id.
     */
    @Override
    public UserResponse getById(Long id) {

        log.debug("Fetching user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", id);
                    return new ResourceNotFoundException(
                            "User not found with id: " + id
                    );
                });

        return userConverter.toResponse(user);
    }
}