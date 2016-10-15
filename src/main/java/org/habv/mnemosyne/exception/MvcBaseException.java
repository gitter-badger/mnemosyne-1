package org.habv.mnemosyne.exception;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 14/10/2016
 */
public class MvcBaseException extends RuntimeException {

    private static final long serialVersionUID = 9084173382903094219L;

    public MvcBaseException(String message) {
        super(message);
    }

    public MvcBaseException(String message, Throwable cause) {
        super(message, cause);
    }

}
