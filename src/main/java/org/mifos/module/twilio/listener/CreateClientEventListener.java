package org.mifos.module.twilio.listener;

import org.mifos.module.twilio.domain.Client;
import org.mifos.module.twilio.domain.CreateClientResponse;
import org.mifos.module.twilio.event.CreateClientEvent;
import org.mifos.module.twilio.provider.RestAdapterProvider;
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

@Component
public class CreateClientEventListener implements ApplicationListener<CreateClientEvent> {

    @Value("${mifos.authtoken}")
    private String authToken;

    @Value("${mifos.tenant}")
    private String tenant;

    private static final Logger logger = LoggerFactory.getLogger(CreateClientEventListener.class);

    private final RestAdapterProvider restAdapterProvider;
    private final TwilioRestClientProvider twilioRestClientProvider;
    private final JsonParser jsonParser;

    @Autowired
    public CreateClientEventListener(final RestAdapterProvider restAdapterProvider,
                                     final TwilioRestClientProvider twilioRestClientProvider,
                                     final JsonParser jsonParser) {
        super();
        this.twilioRestClientProvider = twilioRestClientProvider;
        this.restAdapterProvider = restAdapterProvider;
        this.jsonParser = jsonParser;
    }

    @Override
    public void onApplicationEvent(CreateClientEvent createClientEvent) {
        logger.info("Create client event received, trying to process ...");
        final CreateClientResponse createClientResponse = this.jsonParser.parse(createClientEvent.getPayload(), CreateClientResponse.class);

        final long clientId = createClientResponse.getClientId();

        final RestAdapter restAdapter = this.restAdapterProvider.get();

        final MifosClientService clientService = restAdapter.create(MifosClientService.class);

        final Client client = clientService.findClient(this.authToken, this.tenant, clientId);
        final String mobileNo = client.getMobileNo();
        if (mobileNo != null) {
            logger.info("Mobile number found, sending message!");

            final String message = new StringBuilder("Hello ")
                    .append(client.getDisplayName())
                    .append(", welcome at Mifos!")
                    .toString();

            this.twilioRestClientProvider.sendMessage(mobileNo, message);
        }
        logger.info("Create client event processed!");
    }
}
