package org.mifos.module.sms.service;

import org.mifos.module.sms.event.CreateClientEvent;
import org.mifos.module.sms.event.EventType;
import org.mifos.module.sms.event.LoanRepaymentEvent;
import org.mifos.module.sms.event.SendSMSEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

@Service
public class MifosSMSBridgeService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher eventPublisher;

    public MifosSMSBridgeService() {
        super();
    }

    public void sendShortMessage(final String entity, final String action, final String payload) {

        final EventType eventType = EventType.get(entity, action);

        switch (eventType) {
            case CREATE_CLIENT:
                this.eventPublisher.publishEvent(new CreateClientEvent(this, payload));
                break;
            case LOAN_REPAYMENT:
                this.eventPublisher.publishEvent(new LoanRepaymentEvent(this, payload));
                break;
            case SEND_SMS:
                this.eventPublisher.publishEvent(new SendSMSEvent(this, payload));
                break;
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
