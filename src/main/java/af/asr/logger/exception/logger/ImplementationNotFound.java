package af.asr.logger.exception.logger;

import af.asr.logger.exception.BaseUncheckedException;

public class ImplementationNotFound extends BaseUncheckedException {

    /**
     * Unique id for serialization
     */
    private static final long serialVersionUID = 105555533L;

    /**
     * @param errorCode    unique exception code
     * @param errorMessage exception message
     */
    public ImplementationNotFound(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

}