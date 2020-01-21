package dtu.ws.exception;

/**
 * @author Marcus August Christiansen - s175185
 */

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
