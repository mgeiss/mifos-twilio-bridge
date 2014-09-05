package org.mifos.module.twilio.event;

public class CreateClientEvent extends AbstractEvent {
    public CreateClientEvent(Object source, String payload) {
        super(source, payload);
    }
}
