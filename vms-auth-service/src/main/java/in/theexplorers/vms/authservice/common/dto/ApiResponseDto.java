package in.theexplorers.vms.authservice.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Standard API response wrapper used for all REST responses.
 *
 * @param <T> payload type
 * @author Md Wasif Ali
 * @version 1.0.0
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard API response wrapper")
public class ApiResponseDto<T> {

    @Schema(
            description = "HTTP status of the response",
            example = "OK"
    )
    private HttpStatus status;

    @Schema(
            description = "Actual response payload"
    )
    private T payload;

    @Schema(
            description = "Response message",
            example = "Request processed successfully"
    )
    private String message;

    @Schema(
            description = "Timestamp of response",
            example = "2026-04-07T20:45:00"
    )
    private LocalDateTime timestamp;

    /**
     * Builds success response
     */
    public static <T> ApiResponseDto<T> success(
            HttpStatus status,
            T payload,
            String message
    ) {
        return ApiResponseDto.<T>builder()
                .status(status)
                .payload(payload)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * Builds error response
     */
    public static <T> ApiResponseDto<T> error(
            HttpStatus status,
            String message
    ) {
        return ApiResponseDto.<T>builder()
                .status(status)
                .payload(null)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}