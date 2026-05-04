package in.theexplorers.vms.authservice.user.service.impl;

import in.theexplorers.vms.authservice.common.exception.BadRequestException;
import in.theexplorers.vms.authservice.common.exception.ConflictException;
import in.theexplorers.vms.authservice.common.exception.ResourceNotFoundException;
import in.theexplorers.vms.authservice.common.exception.UnauthorizedException;
import in.theexplorers.vms.authservice.security.util.SecurityUtil;
import in.theexplorers.vms.authservice.user.dto.CreateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UpdateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UserResponse;
import in.theexplorers.vms.authservice.user.entity.User;
import in.theexplorers.vms.authservice.user.converter.UserConverter;
import in.theexplorers.vms.authservice.user.repository.UserRepository;
import in.theexplorers.vms.authservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;


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

    @Override
    public Page<UserResponse> getAllUsers(int page, int size, String active) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdOn").descending());

        Page<User> users;

        switch (active.toLowerCase()) {
            case "true":
                users = userRepository.findByIsActiveTrue(pageable);
                break;
            case "false":
                users = userRepository.findByIsActiveFalse(pageable);
                break;
            case "all":
                users = userRepository.findAll(pageable);
                break;
            default:
                throw new BadRequestException("Invalid active filter");
        }

        return users.map(userConverter::toResponse);
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
    public UserResponse getUserById(Long id) {

        log.debug("Fetching user with id: {}", id);

        Long currentUserId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = hasRole("ADMIN");
        boolean isSecurity = hasRole("SECURITY");

        if (!isAdmin && !isSecurity && !id.equals(currentUserId)) {
            throw new UnauthorizedException("You can only access your own data");
        }

        if (!user.getIsActive()) {
            throw new ResourceNotFoundException("User is inactive");
        }

        return userConverter.toResponse(user);
    }

    /**
     * @param id
     * @param request
     * @return
     */
    @Override
    public UserResponse updateUser(Long id, UpdateUserRequest request) {

        Long currentUserId = SecurityUtil.getCurrentUserId();

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean isAdmin = hasRole("ADMIN");

        if (!isAdmin && !id.equals(currentUserId)) {
            throw new UnauthorizedException("You can only update your own profile");
        }

        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());

        User updated = userRepository.save(user);

        return userConverter.toResponse(updated);
    }

    /**
     * @param id
     */
    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setIsActive(false);

        userRepository.save(user);
    }

    /**
     * @param id
     * @param active
     */
    @Override
    public void updateStatus(Long id, boolean active) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setIsActive(active);

        userRepository.save(user);
    }

    private boolean hasRole(String role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }
}