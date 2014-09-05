package org.mifos.module.twilio.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import retrofit.RestAdapter;

@Component
public class RestAdapterProvider {

    @Value("${mifos.endpoint}")
    private String endPoint;

    public RestAdapterProvider() {
        super();
    }

    public RestAdapter get() {
        final RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(this.endPoint)
                .build();
        return restAdapter;
    }
}
