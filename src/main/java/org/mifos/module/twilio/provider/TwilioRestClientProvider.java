package org.mifos.module.twilio.provider;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TwilioRestClientProvider {

    private static final Logger logger = LoggerFactory.getLogger(TwilioRestClientProvider.class);

    @Value("${mifos.twilio.accountid}")
    private String accountId;

    @Value("${mifos.twilio.authtoken}")
    private String authToken;

    @Value("${mifos.twilio.phoneno}")
    private String phoneNo;

    public TwilioRestClientProvider() {
        super();
    }

    public TwilioRestClient get() {
        final TwilioRestClient client = new TwilioRestClient(accountId, authToken);

        return client;
    }

    public boolean sendMessage(final String mobileNo, final String message) {
        final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
        messageParams.add(new BasicNameValuePair("From", this.phoneNo));
        messageParams.add(new BasicNameValuePair("To", "+" + mobileNo));
        messageParams.add(new BasicNameValuePair("Body", message));

        final TwilioRestClient twilioRestClient = this.get();
        final Account account = twilioRestClient.getAccount();
        final MessageFactory messageFactory = account.getMessageFactory();

        try {
            messageFactory.create(messageParams);
            return true;
        } catch (TwilioRestException trex) {
            logger.error("Could not send message, reason:", trex);
            return false;
        }
    }
}
