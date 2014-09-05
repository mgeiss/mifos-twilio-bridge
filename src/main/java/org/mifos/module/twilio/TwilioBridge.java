package org.mifos.module.twilio;

import org.mifos.module.twilio.configuration.TwilioBridgeConfiguration;
import org.springframework.boot.SpringApplication;

public class TwilioBridge {

    public TwilioBridge() {
        super();
    }

    public static void main(String[] args) {
        SpringApplication.run(TwilioBridgeConfiguration.class, args);
    }
}
