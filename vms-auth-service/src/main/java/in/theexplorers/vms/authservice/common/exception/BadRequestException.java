package in.theexplorers.vms.authservice.common.exception;

/**
 * Thrown when request data is invalid.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}