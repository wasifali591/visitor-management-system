package in.theexplorers.vms.authservice.common.exception;

/**
 * Thrown when user is not authorized.
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }
}