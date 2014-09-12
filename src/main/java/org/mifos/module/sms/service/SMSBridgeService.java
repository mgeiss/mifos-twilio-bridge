package org.mifos.module.sms.service;

import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.mifos.module.sms.event.CreateClientEvent;
import org.mifos.module.sms.event.EventType;
import org.mifos.module.sms.event.LoanRepaymentEvent;
import org.mifos.module.sms.event.SendSMSEvent;
import org.mifos.module.sms.repository.SMSBridgeConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SMSBridgeService implements ApplicationEventPublisherAware {

    private final Logger logger = LoggerFactory.getLogger(SMSBridgeService.class);
    private final SMSBridgeConfigRepository smsBridgeConfigRepository;
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public SMSBridgeService(final SMSBridgeConfigRepository smsBridgeConfigRepository) {
        super();
        this.smsBridgeConfigRepository = smsBridgeConfigRepository;
    }

    public void sendShortMessage(final String entity, final String action, final String tenantId, final String payload) {

        final EventType eventType = EventType.get(entity, action);

        switch (eventType) {
            case CREATE_CLIENT:
                this.eventPublisher.publishEvent(new CreateClientEvent(this, tenantId, payload));
                break;
            case LOAN_REPAYMENT:
                this.eventPublisher.publishEvent(new LoanRepaymentEvent(this, tenantId, payload));
                break;
            case SEND_SMS:
                this.eventPublisher.publishEvent(new SendSMSEvent(this, tenantId, payload));
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
}
