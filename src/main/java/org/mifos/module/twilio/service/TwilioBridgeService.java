package org.mifos.module.twilio.service;

import org.mifos.module.twilio.event.CreateClientEvent;
import org.mifos.module.twilio.event.EventType;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class TwilioBridgeService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    public TwilioBridgeService() {
        super();
    }

    public void sendShortMessage(final String entity, final String action, final String payload) {

        final EventType eventType = EventType.get(entity, action);

        switch (eventType) {
            case CREATE_CLIENT:
                this.eventPublisher.publishEvent(new CreateClientEvent(this, payload));
                break;
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
