package org.habv.mnemosyne.exception;

/**
 * @author Herman Barrantes
 * @since 0.1.0
 * @date 14/10/2016
 */
public class MvcNotFoundException extends MvcBaseException {

    private static final long serialVersionUID = -7104395070535019089L;

    public MvcNotFoundException(String message) {
        super(message);
    }

    public MvcNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
