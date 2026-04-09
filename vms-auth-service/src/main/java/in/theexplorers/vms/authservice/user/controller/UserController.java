package in.theexplorers.vms.authservice.user.controller;

import in.theexplorers.vms.authservice.common.dto.ApiResponseDto;
import in.theexplorers.vms.authservice.user.dto.CreateUserRequest;
import in.theexplorers.vms.authservice.user.dto.UserResponse;
import in.theexplorers.vms.authservice.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for user operations.
 */
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * Create new user.
     */
    @Operation(summary = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "Duplicate user")
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<UserResponse>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

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
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<UserResponse>> getUser(
            @Parameter(description = "User ID")
            @PathVariable @Positive Long id) {

        return ResponseEntity.ok(
                ApiResponseDto.success(
                        HttpStatus.OK,
                        userService.getById(id),
                        "User fetched successfully"
                )
        );
    }
}