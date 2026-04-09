package in.theexplorers.vms.authservice.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Response DTO for user.
 */
@Data
@Schema(description = "User response")
public class UserResponse {

    private Long id;

    private String username;

    private String email;
}