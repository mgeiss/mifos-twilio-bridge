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
package org.mifos.module.sms.service;

import org.mifos.module.sms.exception.InvalidApiKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.PostConstruct;

@Service
public class SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Value("${mifos.smsgatewayprovider.accountid}")
    private String twilioAccountId;

    @Value("${mifos.smsgatewayprovider.authtoken}")
    private String twilioAuthToken;

    @Value("${mifos.authtoken}")
    private String mifosAuthToken;

    private String apiKey;

    public SecurityService() {
        super();
    }

    public void verifyApiKey(final String testee) {
        if (!this.apiKey.equals(testee)) {
            throw new InvalidApiKeyException(testee);
        }
    }

    @PostConstruct
    void initApiKey() {
        try {
            final String source = this.mifosAuthToken + ":" + this.twilioAccountId + ":" + this.twilioAuthToken;
            this.apiKey = DigestUtils.md5DigestAsHex(source.getBytes("UTF-8"));

            logger.info("Your API key: " + this.apiKey);
        } catch (Exception ex) {
            logger.error("Could not create API key, reason,", ex);
        }
    }
}
