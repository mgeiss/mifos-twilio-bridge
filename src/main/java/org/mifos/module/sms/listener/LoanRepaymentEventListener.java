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

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.mifos.module.sms.domain.*;
import org.mifos.module.sms.event.LoanRepaymentEvent;
import org.mifos.module.sms.exception.SMSGatewayException;
import org.mifos.module.sms.parser.JsonParser;
import org.mifos.module.sms.provider.RestAdapterProvider;
import org.mifos.module.sms.provider.SMSGateway;
import org.mifos.module.sms.provider.SMSGatewayProvider;
import org.mifos.module.sms.repository.EventSourceRepository;
import org.mifos.module.sms.repository.SMSBridgeConfigRepository;
import org.mifos.module.sms.service.MifosClientService;
import org.mifos.module.sms.service.MifosLoanService;
import org.mifos.module.sms.util.AuthorizationTokenBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import java.io.StringWriter;
import java.util.Date;

@Component
public class LoanRepaymentEventListener implements ApplicationListener<LoanRepaymentEvent> {

    private static final Logger logger = LoggerFactory.getLogger(CreateClientEventListener.class);

    @Value("${message.template.loanrepayment}")
    private String messageTemplate;

    private final SMSBridgeConfigRepository smsBridgeConfigRepository;
    private final EventSourceRepository eventSourceRepository;
    private final RestAdapterProvider restAdapterProvider;
    private final SMSGatewayProvider smsGatewayProvider;
    private final JsonParser jsonParser;

    @Autowired
    public LoanRepaymentEventListener(final SMSBridgeConfigRepository smsBridgeConfigRepository,
                                      final EventSourceRepository eventSourceRepository,
                                      final RestAdapterProvider restAdapterProvider,
                                      final SMSGatewayProvider smsGatewayProvider,
                                      final JsonParser jsonParser)
                                       {
        this.smsBridgeConfigRepository = smsBridgeConfigRepository;
        this.eventSourceRepository = eventSourceRepository;
        this.restAdapterProvider = restAdapterProvider;
        this.smsGatewayProvider = smsGatewayProvider;
        this.jsonParser = jsonParser;
        
    }

    @Transactional
    @Override
    public void onApplicationEvent(LoanRepaymentEvent loanRepaymentEvent) {
        logger.info("Loan repayment event received, trying to process ...");

        final EventSource eventSource = this.eventSourceRepository.findOne(loanRepaymentEvent.getEventId());
         
        final SMSBridgeConfig smsBridgeConfig = this.smsBridgeConfigRepository.findByTenantId(eventSource.getTenantId());
        if (smsBridgeConfig == null) {
            logger.error("Unknown tenant " + eventSource.getTenantId() + "!");
            return;
        }

        final LoanRepaymentResponse loanRepaymentResponse = this.jsonParser.parse(eventSource.getPayload(), LoanRepaymentResponse.class);

        final long clientId = loanRepaymentResponse.getClientId();
        final long loanId = loanRepaymentResponse.getLoanId();

        final RestAdapter restAdapter = this.restAdapterProvider.get(smsBridgeConfig);
        try {
            final String authToken = AuthorizationTokenBuilder.token(smsBridgeConfig.getMifosToken()).build();
            final MifosLoanService loanService = restAdapter.create(MifosLoanService.class);
            final Loan loan = loanService.findLoan(authToken, smsBridgeConfig.getTenantId(), loanId);

            final MifosClientService clientService = restAdapter.create(MifosClientService.class);
            final Client client = clientService.findClient(authToken, smsBridgeConfig.getTenantId(), clientId);

            final String mobileNo = client.getMobileNo();
            if (mobileNo != null) {
                logger.info("Mobile number found, sending message!");

                final VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("name", client.getDisplayName());
                velocityContext.put("account", loan.getAccountNo());

                final StringWriter stringWriter = new StringWriter();
                Velocity.evaluate(velocityContext, stringWriter, "LoanRepaymentMessage", this.messageTemplate);

                final SMSGateway smsGateway = this.smsGatewayProvider.get(smsBridgeConfig.getSmsProvider());
                smsGateway.sendMessage(smsBridgeConfig, mobileNo, stringWriter.toString());
            }
            eventSource.setProcessed(Boolean.TRUE);
            
            logger.info("Loan repayment event processed!");
        } catch (RetrofitError rer) {
            if (rer.getResponse().getStatus() == 404) {
                logger.info("Loan not found!");
            }
            eventSource.setProcessed(Boolean.FALSE);
            eventSource.setErrorMessage(rer.getMessage());
        } catch (SMSGatewayException sgex) {
            eventSource.setProcessed(Boolean.FALSE);
            eventSource.setErrorMessage(sgex.getMessage());
        }
        eventSource.setLastModifiedOn(new Date());
        this.eventSourceRepository.save(eventSource);
    }
}
