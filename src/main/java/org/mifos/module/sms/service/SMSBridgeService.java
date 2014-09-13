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
import java.util.List;

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

        this.publishEvent(eventType, eventId);
    }

    public SMSBridgeConfig findSmsBridgeConfigByTenantId(final String tenantId) {
        final SMSBridgeConfig smsBridgeConfig = this.smsBridgeConfigRepository.findByTenantId(tenantId);
        return smsBridgeConfig;
    }

    @Transactional
    public Long createSmsBridgeConfig(final SMSBridgeConfig smsBridgeConfig) {
        final Date now = new Date();
        smsBridgeConfig.setCreatedOn(now);
        smsBridgeConfig.setLastModifiedOn(now);
        final SMSBridgeConfig newSMSmsBridgeConfig = this.smsBridgeConfigRepository.save(smsBridgeConfig);
        return newSMSmsBridgeConfig.getId();
    }

    @Transactional
    public void deleteSmsBridgeConfig(final Long id) {
        this.smsBridgeConfigRepository.delete(id);
    }

    public List<EventSource> findEventsSourcesByTenantId(final String tenantId) {
        return this.eventSourceRepository.findByTenantId(tenantId);
    }

    public void resendEventsSourcesByTenantId(final String tenantId) {
        final List<EventSource> eventSources = this.eventSourceRepository.findByTenantIdAndProcessed(tenantId, Boolean.FALSE);
        if (eventSources != null) {
            for (EventSource eventSource : eventSources) {
                final EventType eventType = EventType.get(eventSource.getEntity(), eventSource.getAction());

                this.publishEvent(eventType, eventSource.getId());
            }
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    private void publishEvent(final EventType eventType, final Long eventId) {
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

    private Long saveEvent(final String tenantId,
                           final String entity,
                           final String action,
                           final String payload) {
        final EventSource eventSource = new EventSource();
        eventSource.setTenantId(tenantId);
        eventSource.setEntity(entity);
        eventSource.setAction(action);
        eventSource.setPayload(payload);
        eventSource.setProcessed(Boolean.FALSE);
        final Date now = new Date();
        eventSource.setCreatedOn(now);
        eventSource.setLastModifiedOn(now);

        return this.eventSourceRepository.save(eventSource).getId();
    }
}
