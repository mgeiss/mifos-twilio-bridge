package org.mifos.module.twilio.configuration;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

public class TwilioBridgeInitializer extends SpringBootServletInitializer {

    public TwilioBridgeInitializer() {
        super();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TwilioBridgeConfiguration.class);
    }
}
