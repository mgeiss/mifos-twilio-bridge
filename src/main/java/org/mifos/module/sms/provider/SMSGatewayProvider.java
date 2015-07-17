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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SMSGatewayProvider implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public SMSGateway get(final String smsGatewayProvider) {
        if (smsGatewayProvider.equalsIgnoreCase("twilio")) {
            return this.applicationContext.getBean(TwilioRestClientProvider.class);
        }else if(smsGatewayProvider.equalsIgnoreCase("africastalking")){
        	return this.applicationContext.getBean(AfricasRestClientProvider.class);
        }

        throw new UnsupportedOperationException("Unsupported SMS Gateway Provider: " + smsGatewayProvider);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
