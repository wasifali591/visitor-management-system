package in.theexplorers.vms.authservice.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * DTO for updating user details.
 *
 * <p>
 * Only editable fields are exposed here.
 * Sensitive fields like password, roles, etc. are NOT included.
 * </p>
 *
 * @author Md Wasif Ali
 * @version 1.0.0
 */
@Data
@Schema(description = "Request DTO for updating user details")
public class UpdateUserRequest {

    @Email(message = "Invalid email format")
    @Schema(
            description = "User email address",
            example = "wasif@gmail.com"
    )
    private String email;

    @Size(max = 100, message = "Full name must not exceed 100 characters")
    @Schema(
            description = "Full name of the user",
            example = "Md Wasif Ali"
    )
    private String fullName;
}