package org.mifos.module.sms.provider;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.mifos.module.sms.exception.SMSGatewayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AfricasRestClientProvider implements SMSGateway {
	
	private static final Logger logger = LoggerFactory.getLogger(AfricasRestClientProvider.class);

	AfricasRestClientProvider() {
		super();
    }
	@Override
	public void sendMessage(final SMSBridgeConfig smsBridgeConfig, final String mobileNo, final String message)
	        throws SMSGatewayException {              
             String username = smsBridgeConfig.getSmsProviderToken();
             String apiKey   = smsBridgeConfig.getSmsProviderAccountId();
             
             AfricasTalkingGateway gateway  = new AfricasTalkingGateway(username, apiKey);
    
            try {
            JSONArray results = gateway.sendMessage(mobileNo, message);
            logger.debug("Loan Repayment Reminder Sms  to event processed!");
                   for( int i = 0; i < results.length(); ++i ) {
                  JSONObject result = results.getJSONObject(i);
                  System.out.print(result.getString("status") + ","); 
                  System.out.print(result.getString("number") + ",");
                  System.out.print(result.getString("messageId") + ",");
                  System.out.println(result.getString("cost"));
        }
       }
       
       catch (Exception e) {
        System.out.println("Encountered an error while sending " + e.getMessage());
        }    
   }	
}
