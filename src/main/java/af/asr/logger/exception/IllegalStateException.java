package af.asr.logger.exception;

public class IllegalStateException extends BaseUncheckedException {

    private static final long serialVersionUID = 105555532L;

    public IllegalStateException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public IllegalStateException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

}