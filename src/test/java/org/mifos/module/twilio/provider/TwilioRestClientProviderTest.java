package org.mifos.module.twilio.provider;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.instance.Account;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mifos.module.twilio.configuration.TwilioBridgeTestConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {TwilioBridgeTestConfiguration.class})
public class TwilioRestClientProviderTest {

    @Autowired
    private TwilioRestClientProvider twilioRestClientProvider;

    @Test
    public void shouldReceiveAccount() {
        final TwilioRestClient client = this.twilioRestClientProvider.get();

        final Account account = client.getAccount();
        try {
            final String friendlyName = account.getFriendlyName();
            Assert.assertEquals("Mifos X", friendlyName);
        } catch (RuntimeException rex) {
            Assert.fail(rex.getMessage());
        }
    }
}
