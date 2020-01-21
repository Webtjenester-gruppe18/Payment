package dtu.ws.exception;

/**
 * @author Marcus August Christiansen - s175185
 */

public class ExceptionContainer {

    private String errorMessage;

    public ExceptionContainer() {}

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
