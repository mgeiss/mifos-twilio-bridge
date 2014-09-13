package org.mifos.module.sms.service;

import org.mifos.module.sms.domain.EventSource;
import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.mifos.module.sms.event.CreateClientEvent;
import org.mifos.module.sms.event.EventType;
import org.mifos.module.sms.event.LoanRepaymentEvent;
import org.mifos.module.sms.event.SendSMSEvent;
import org.mifos.module.sms.repository.EventSourceRepository;
import org.mifos.module.sms.repository.SMSBridgeConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class SMSBridgeService implements ApplicationEventPublisherAware {

    private final Logger logger = LoggerFactory.getLogger(SMSBridgeService.class);

    private final SMSBridgeConfigRepository smsBridgeConfigRepository;
    private final EventSourceRepository eventSourceRepository;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public SMSBridgeService(final SMSBridgeConfigRepository smsBridgeConfigRepository,
                            final EventSourceRepository eventSourceRepository) {
        super();
        this.smsBridgeConfigRepository = smsBridgeConfigRepository;
        this.eventSourceRepository = eventSourceRepository;
    }

    public void sendShortMessage(final String entity, final String action, final String tenantId, final String payload) {

        final EventType eventType = EventType.get(entity, action);

        final Long eventId = this.saveEvent(tenantId, entity, action, payload);

        switch (eventType) {
            case CREATE_CLIENT:
                this.eventPublisher.publishEvent(new CreateClientEvent(this, eventId));
                break;
            case LOAN_REPAYMENT:
                this.eventPublisher.publishEvent(new LoanRepaymentEvent(this, eventId));
                break;
            case SEND_SMS:
                this.eventPublisher.publishEvent(new SendSMSEvent(this, eventId));
                break;
        }
    }

    public SMSBridgeConfig findById(final Long id) {
        return this.smsBridgeConfigRepository.findOne(id);
    }

    public SMSBridgeConfig findByTenantId(final String tenantId) {
        final SMSBridgeConfig smsBridgeConfig = this.smsBridgeConfigRepository.findByTenantId(tenantId);
        return smsBridgeConfig;
    }

    @Transactional
    public Long create(final SMSBridgeConfig smsBridgeConfig) {
        final Date now = new Date();
        smsBridgeConfig.setCreatedOn(now);
        smsBridgeConfig.setLastModifiedOn(now);
        final SMSBridgeConfig newSMSmsBridgeConfig = this.smsBridgeConfigRepository.save(smsBridgeConfig);
        return newSMSmsBridgeConfig.getId();
    }

    @Transactional
    public void update(final SMSBridgeConfig smsBridgeConfig) {
        this.smsBridgeConfigRepository.save(smsBridgeConfig);
    }

    @Transactional
    public void delete(final Long id) {
        this.smsBridgeConfigRepository.delete(id);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    private Long saveEvent(final String tenantId,
                           final String entity,
                           final String action,
                           final String payload) {
        final EventSource eventSource = new EventSource();
        eventSource.setTenantId(tenantId);
        eventSource.setEntity(entity);
        eventSource.setAction(action);
        eventSource.setPayload(payload);
        final Date now = new Date();
        eventSource.setCreatedOn(now);
        eventSource.setLastModifiedOn(now);

        return this.eventSourceRepository.save(eventSource).getId();
    }
}
