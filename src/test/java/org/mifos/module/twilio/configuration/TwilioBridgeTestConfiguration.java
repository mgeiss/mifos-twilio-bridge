package org.mifos.module.twilio.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {
        "org.mifos.module.twilio.controller",
        "org.mifos.module.twilio.service",
        "org.mifos.module.twilio.provider",
        "org.mifos.module.twilio.listener"})
public class TwilioBridgeTestConfiguration {

    public TwilioBridgeTestConfiguration() {
        super();
    }
}
