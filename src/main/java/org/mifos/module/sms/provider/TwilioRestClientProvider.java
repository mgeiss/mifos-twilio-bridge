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
package org.mifos.module.sms.provider;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TwilioRestClientProvider implements SMSGateway {

    private static final Logger logger = LoggerFactory.getLogger(TwilioRestClientProvider.class);

    TwilioRestClientProvider() {
        super();
    }

    TwilioRestClient get(final SMSBridgeConfig smsBridgeConfig) {
        final TwilioRestClient client = new TwilioRestClient(smsBridgeConfig.getSmsProviderAccountId(), smsBridgeConfig.getSmsProviderToken());

        return client;
    }

    public boolean sendMessage(final SMSBridgeConfig smsBridgeConfig, final String mobileNo, final String message) {
        final List<NameValuePair> messageParams = new ArrayList<NameValuePair>();
        messageParams.add(new BasicNameValuePair("From", smsBridgeConfig.getPhoneNo()));
        messageParams.add(new BasicNameValuePair("To", "+" + mobileNo));
        messageParams.add(new BasicNameValuePair("Body", message));

        final TwilioRestClient twilioRestClient = this.get(smsBridgeConfig);
        final Account account = twilioRestClient.getAccount();
        final MessageFactory messageFactory = account.getMessageFactory();

        try {
            logger.info("Sending SMS to " + mobileNo + " ...");
            messageFactory.create(messageParams);
            return true;
        } catch (TwilioRestException trex) {
            logger.error("Could not send message, reason:", trex);
            return false;
        }
    }
}
