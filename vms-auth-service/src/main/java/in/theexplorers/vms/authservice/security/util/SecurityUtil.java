package in.theexplorers.vms.authservice.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for security context.
 */
public class SecurityUtil {

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // assuming principal contains userId
        return (Long) authentication.getPrincipal();
    }
}