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
import org.mifos.module.twilio.domain.CreateClientResponse;
import org.mifos.module.twilio.event.CreateClientEvent;
import org.mifos.module.twilio.provider.RestAdapterProvider;
import org.mifos.module.twilio.provider.SMSGateway;
import org.mifos.module.twilio.provider.SMSGatewayProvider;
import org.mifos.module.twilio.provider.TwilioRestClientProvider;
import org.mifos.module.twilio.service.MifosClientService;
import org.mifos.module.twilio.parser.JsonParser;
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
public class CreateClientEventListener implements ApplicationListener<CreateClientEvent> {

    @Value("${mifos.authtoken}")
    private String authToken;

    @Value("${mifos.tenant}")
    private String tenant;

    @Value("${message.template.createclient}")
    private String messageTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CreateClientEventListener.class);

    private final RestAdapterProvider restAdapterProvider;
    private final SMSGatewayProvider smsGatewayProvider;
    private final JsonParser jsonParser;

    @Autowired
    public CreateClientEventListener(final RestAdapterProvider restAdapterProvider,
                                     final SMSGatewayProvider smsGatewayProvider,
                                     final JsonParser jsonParser) {
        super();
        this.restAdapterProvider = restAdapterProvider;
        this.smsGatewayProvider = smsGatewayProvider;
        this.jsonParser = jsonParser;
    }

    @Override
    public void onApplicationEvent(CreateClientEvent createClientEvent) {
        logger.info("Create client event received, trying to process ...");
        final CreateClientResponse createClientResponse = this.jsonParser.parse(createClientEvent.getPayload(), CreateClientResponse.class);

        final long clientId = createClientResponse.getClientId();

        final RestAdapter restAdapter = this.restAdapterProvider.get();

        try {
            final MifosClientService clientService = restAdapter.create(MifosClientService.class);

            final Client client = clientService.findClient(this.authToken, this.tenant, clientId);
            final String mobileNo = client.getMobileNo();
            if (mobileNo != null) {
                logger.info("Mobile number found, sending message!");

                final VelocityContext velocityContext = new VelocityContext();
                velocityContext.put("name", client.getDisplayName());

                final StringWriter stringWriter = new StringWriter();
                Velocity.evaluate(velocityContext, stringWriter, "CreateClientMessage", this.messageTemplate);

                final SMSGateway smsGateway = this.smsGatewayProvider.get();
                smsGateway.sendMessage(mobileNo, stringWriter.toString());
            }
            logger.info("Create client event processed!");
        } catch (RetrofitError rer) {
            if (rer.getResponse().getStatus() == 404) {
                logger.info("Client not found!");
            }
        }

    }
}
