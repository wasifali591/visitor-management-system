package in.theexplorers.vms.authservice.common.exception;

import in.theexplorers.vms.authservice.common.dto.ApiResponseDto;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for all controllers.
 * Converts exceptions into standardized API response.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles @Valid validation errors (RequestBody DTO validation)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        log.warn("Validation failed: {}", errors);

        return ResponseEntity.badRequest()
                .body(ApiResponseDto.<Map<String, String>>builder()
                        .status(HttpStatus.BAD_REQUEST)
                        .payload(errors)
                        .message("Validation failed")
                        .timestamp(LocalDateTime.now())
                        .build());
    }

    /**
     * Handles validation for path variable / request param
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponseDto<String>> handleConstraintViolation(
            ConstraintViolationException ex) {

        log.warn("Constraint violation: {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(ApiResponseDto.error(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()
                ));
    }

    /**
     * Resource not found exception
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleNotFound(
            ResourceNotFoundException ex) {

        log.error("Resource not found: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.error(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                ));
    }

    /**
     * Bad request exception
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleBadRequest(
            BadRequestException ex) {

        log.warn("Bad request: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error(
                        HttpStatus.BAD_REQUEST,
                        ex.getMessage()
                ));
    }

    /**
     * Conflict exception
     */
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleConflict(
            ConflictException ex) {

        log.warn("Conflict: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.error(
                        HttpStatus.CONFLICT,
                        ex.getMessage()
                ));
    }

    /**
     * Unauthorized exception
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleUnauthorized(
            UnauthorizedException ex) {

        log.warn("Unauthorized: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponseDto.error(
                        HttpStatus.UNAUTHORIZED,
                        ex.getMessage()
                ));
    }

    /**
     * Fallback global exception
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGlobal(
            Exception ex) {

        log.error("Unexpected error occurred", ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Something went wrong"
                ));
    }
}