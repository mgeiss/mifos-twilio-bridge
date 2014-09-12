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
import org.mifos.module.sms.repository.SMSBridgeConfigRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SMSBridgeConfigService {

    private final SMSBridgeConfigRepository smsBridgeConfigRepository;

    public SMSBridgeConfigService(SMSBridgeConfigRepository smsBridgeConfigRepository) {
        super();
        this.smsBridgeConfigRepository = smsBridgeConfigRepository;
    }

    public SMSBridgeConfig findById(final Long id) {
        return this.smsBridgeConfigRepository.findOne(id);
    }

    public SMSBridgeConfig findByTenant(final String tenant) {
        return this.smsBridgeConfigRepository.findByTenenat(tenant);
    }

    @Transactional
    public Long create(final SMSBridgeConfig smsBridgeConfig) {
        final SMSBridgeConfig newSMSmsBridgeConfig = this.smsBridgeConfigRepository.save(smsBridgeConfig);
        return newSMSmsBridgeConfig.getId();
    }

    @Transactional
    public void update(final SMSBridgeConfig smsBridgeConfig) {
        this.smsBridgeConfigRepository.save(smsBridgeConfig);
    }

    @Transactional
    public void delete(final Long id) {
        this.smsBridgeConfigRepository.delete(id);
    }
}
