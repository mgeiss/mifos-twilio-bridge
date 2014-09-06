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

import org.mifos.module.twilio.domain.SendSMSResponse;
import org.mifos.module.twilio.event.SendSMSEvent;
import org.mifos.module.twilio.parser.JsonParser;
import org.mifos.module.twilio.provider.TwilioRestClientProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SendSMSEventListener implements ApplicationListener<SendSMSEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SendSMSEventListener.class);

    private final TwilioRestClientProvider twilioRestClientProvider;
    private final JsonParser jsonParser;

    @Autowired
    public SendSMSEventListener(final TwilioRestClientProvider twilioRestClientProvider,
                                final JsonParser jsonParser) {
        super();
        this.twilioRestClientProvider = twilioRestClientProvider;
        this.jsonParser = jsonParser;
    }

    @Override
    public void onApplicationEvent(final SendSMSEvent sendSMSEvent) {
        logger.info("Send SMS event received, trying to process ...");
        final SendSMSResponse sendSMSResponse = this.jsonParser.parse(sendSMSEvent.getPayload(), SendSMSResponse.class);

        this.twilioRestClientProvider.sendMessage(sendSMSResponse.getMobileNo(), sendSMSResponse.getMessage());
        logger.info("Send SMS event processed!");
    }
}
