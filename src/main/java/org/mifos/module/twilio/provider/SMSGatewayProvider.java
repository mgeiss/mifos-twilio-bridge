package org.mifos.module.twilio.provider;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SMSGatewayProvider implements ApplicationContextAware {

    @Value("${mifos.smsgatewayprovider.name}")
    private String smsGatewayProvider;

    private ApplicationContext applicationContext;

    public SMSGateway get() {
        if (this.smsGatewayProvider.equalsIgnoreCase("twilio")) {
            return this.applicationContext.getBean(TwilioRestClientProvider.class);
        }

        throw new UnsupportedOperationException("Unsupported SMS Gateway Provider: " + this.smsGatewayProvider);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
