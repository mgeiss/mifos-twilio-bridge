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
package org.mifos.module.twilio.listener;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.mifos.module.twilio.domain.Client;
import org.mifos.module.twilio.domain.Loan;
import org.mifos.module.twilio.domain.LoanRepaymentResponse;
import org.mifos.module.twilio.event.LoanRepaymentEvent;
import org.mifos.module.twilio.parser.JsonParser;
import org.mifos.module.twilio.provider.RestAdapterProvider;
import org.mifos.module.twilio.provider.TwilioRestClientProvider;
import org.mifos.module.twilio.service.MifosClientService;
import org.mifos.module.twilio.service.MifosLoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import retrofit.RestAdapter;
import retrofit.RetrofitError;

import java.io.StringWriter;

@Component
public class LoanRepaymentEventListener implements ApplicationListener<LoanRepaymentEvent> {

    @Value("${mifos.authtoken}")
    private String authToken;

    @Value("${mifos.tenant}")
    private String tenant;

    @Value("${message.template.loanrepayment}")
    private String messageTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CreateClientEventListener.class);

    private final RestAdapterProvider restAdapterProvider;
    private final TwilioRestClientProvider twilioRestClientProvider;
    private final JsonParser jsonParser;

    @Autowired
    public LoanRepaymentEventListener(final RestAdapterProvider restAdapterProvider,
                                      final TwilioRestClientProvider twilioRestClientProvider,
                                      final JsonParser jsonParser) {
        this.restAdapterProvider = restAdapterProvider;
        this.twilioRestClientProvider = twilioRestClientProvider;
        this.jsonParser = jsonParser;
    }

    @Override
    public void onApplicationEvent(LoanRepaymentEvent loanRepaymentEvent) {
        logger.info("Loan repayment event received, trying to process ...");
        final LoanRepaymentResponse loanRepaymentResponse = this.jsonParser.parse(loanRepaymentEvent.getPayload(), LoanRepaymentResponse.class);

        final long clientId = loanRepaymentResponse.getClientId();
        final long loanId = loanRepaymentResponse.getLoanId();

        final RestAdapter restAdapter = this.restAdapterProvider.get();
        try {
            final MifosLoanService loanService = restAdapter.create(MifosLoanService.class);
            final Loan loan = loanService.findLoan(this.authToken, this.tenant, loanId);

            final MifosClientService clientService = restAdapter.create(MifosClientService.class);
            final Client client = clientService.findClient(this.authToken, this.tenant, clientId);

            final String mobileNo = client.getMobileNo();
            if (mobileNo != null) {
                logger.info("Mobile number found, sending message!");

                final VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("name", client.getDisplayName());
                velocityContext.put("account", loan.getAccountNo());

                final StringWriter stringWriter = new StringWriter();
                Velocity.evaluate(velocityContext, stringWriter, "LoanRepaymentMessage", this.messageTemplate);

                this.twilioRestClientProvider.sendMessage(mobileNo, stringWriter.toString());
            }
            logger.info("Loan repayment event processed!");
        } catch (RetrofitError rer) {
            if (rer.getResponse().getStatus() == 404) {
                logger.info("Loan not found!");
            }
        }
    }
}
