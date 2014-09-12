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

import org.mifos.module.sms.domain.SMSBridgeConfig;
import org.mifos.module.sms.exception.InvalidApiKeyException;
import org.mifos.module.sms.repository.SMSBridgeConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class SecurityService {

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    @Autowired
    private SMSBridgeConfigRepository smsBridgeConfigRepository;

    public SecurityService() {
        super();
    }

    public void verifyApiKey(final String testee) {
        final List<SMSBridgeConfig> smsBridgeConfigList = this.smsBridgeConfigRepository.findByApiKey(testee);
        if (smsBridgeConfigList == null || smsBridgeConfigList.size() == 0) {
            throw new InvalidApiKeyException(testee);
        }
    }

    public String generateApiKey(final String tenantId,
                                 final String mifosToken,
                                 final String smsProviderAccount,
                                 final String smsProviderToken) {
        try {
            final String source = tenantId + ":" + mifosToken + ":" + smsProviderAccount + ":" + smsProviderToken;
            return DigestUtils.md5DigestAsHex(source.getBytes("UTF-8"));
        } catch (Exception ex) {
            logger.error("Could not create API key, reason,", ex);
            throw new IllegalArgumentException(ex);
        }
    }
}
