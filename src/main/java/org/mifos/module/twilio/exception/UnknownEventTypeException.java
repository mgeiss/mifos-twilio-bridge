package org.mifos.module.twilio.exception;

public class UnknownEventTypeException extends RuntimeException {

    public UnknownEventTypeException(final String entity, final String action) {
        super("Unknown event! entity: " + entity + "; action: " + action);
    }
}
