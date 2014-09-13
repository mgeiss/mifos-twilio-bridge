/**
 * Copyright 2014 Markus Geiss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mifos.module.sms.listener;

import org.mifos.module.sms.domain.EventSource;
import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.mifos.module.sms.domain.SendSMSResponse;
import org.mifos.module.sms.event.SendSMSEvent;
import org.mifos.module.sms.parser.JsonParser;
import org.mifos.module.sms.provider.SMSGateway;
import org.mifos.module.sms.provider.SMSGatewayProvider;
import org.mifos.module.sms.repository.EventSourceRepository;
import org.mifos.module.sms.repository.SMSBridgeConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
public class SendSMSEventListener implements ApplicationListener<SendSMSEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SendSMSEventListener.class);

    private final SMSBridgeConfigRepository smsBridgeConfigRepository;
    private final EventSourceRepository eventSourceRepository;
    private final SMSGatewayProvider smsGatewayProvider;
    private final JsonParser jsonParser;

    @Autowired
    public SendSMSEventListener(final SMSBridgeConfigRepository smsBridgeConfigRepository,
                                final EventSourceRepository eventSourceRepository,
                                final SMSGatewayProvider smsGatewayProvider,
                                final JsonParser jsonParser) {
        super();
        this.smsBridgeConfigRepository = smsBridgeConfigRepository;
        this.eventSourceRepository = eventSourceRepository;
        this.smsGatewayProvider = smsGatewayProvider;
        this.jsonParser = jsonParser;
    }

    @Transactional
    @Override
    public void onApplicationEvent(final SendSMSEvent sendSMSEvent) {
        logger.info("Send SMS event received, trying to process ...");

        final EventSource eventSource = this.eventSourceRepository.findOne(sendSMSEvent.getEventId());

        final SMSBridgeConfig smsBridgeConfig = this.smsBridgeConfigRepository.findByTenantId(eventSource.getTenantId());
        if (smsBridgeConfig == null) {
            logger.error("Unknown tenant " + eventSource.getTenantId() + "!");
            return;
        }

        final SendSMSResponse sendSMSResponse = this.jsonParser.parse(eventSource.getPayload(), SendSMSResponse.class);

        final SMSGateway smsGateway = this.smsGatewayProvider.get(smsBridgeConfig.getSmsProvider());
        if (smsGateway.sendMessage(smsBridgeConfig, sendSMSResponse.getMobileNo(), sendSMSResponse.getMessage())) {
            eventSource.setProcessed(Boolean.TRUE);
            logger.info("Send SMS event processed!");
        } else {
            eventSource.setProcessed(Boolean.FALSE);
        }
        eventSource.setLastModifiedOn(new Date());
        this.eventSourceRepository.save(eventSource);
    }
}
