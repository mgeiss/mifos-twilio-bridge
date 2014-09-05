package org.mifos.module.twilio.event;

import org.springframework.context.ApplicationEvent;

public abstract class AbstractEvent extends ApplicationEvent {

    private final String payload;

    public AbstractEvent(Object source, String payload) {
        super(source);
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
