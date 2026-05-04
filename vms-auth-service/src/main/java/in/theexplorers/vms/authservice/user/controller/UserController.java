package in.theexplorers.vms.authservice.user.controller;

import in.theexplorers.vms.authservice.common.dto.ApiResponseDto;
import in.theexplorers.vms.authservice.user.dto.CreateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UpdateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UserResponse;
import in.theexplorers.vms.authservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user operations.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Create new user.
     */
    @Operation(summary = "Create new user (ADMIN only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Duplicate user")
    })
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponseDto<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        log.info("REST request to create user: {}", request.getUsername());

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        HttpStatus.OK,
                        userService.createUser(request),
                        "User created successfully"
                )
        );
    }

    /**
     * Get user by id.
     */
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN','SECURITY','EMPLOYEE','GATE_OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponse>> getUser(
            @Parameter(description = "User ID")
            @PathVariable @Positive Long id) {
        log.info("Fetching user by id: {}", id);

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        HttpStatus.OK,
                        userService.getUserById(id),
                        "User fetched successfully"
                )
        );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SECURITY')")
    @Operation(summary = "Get all users with pagination")
    public ApiResponseDto<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "true") String active
    ) {
        log.info("Fetching users | page: {} size: {} active: {}", page, size, active);

        return ApiResponseDto.success(
                HttpStatus.OK,
                userService.getAllUsers(page, size, active),
                "Users fetched successfully"
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','GATE_OPERATOR')")
    @Operation(summary = "Update user")
    public ApiResponseDto<?> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {

        log.info("Updating user id: {}", id);

        return ApiResponseDto.success(
                HttpStatus.OK,
                userService.updateUser(id, request),
                "User updated successfully"
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user (soft)")
    public ApiResponseDto<?> deleteUser(@PathVariable Long id) {

        log.info("Deleting user id: {}", id);

        userService.deleteUser(id);

        return ApiResponseDto.success(
                HttpStatus.OK,
                null,
                "User deleted successfully"
        );
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Activate/Deactivate user")
    public ApiResponseDto<?> updateStatus(
            @PathVariable Long id,
            @RequestParam boolean active
    ) {

        log.info("Updating status for user id: {} to {}", id, active);

        userService.updateStatus(id, active);

        return ApiResponseDto.success(
                HttpStatus.OK,
                null,
                "User status updated"
        );
    }
}