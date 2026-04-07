package in.theexplorers.vms.authservice.common.exception;

/**
 * Thrown when requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}