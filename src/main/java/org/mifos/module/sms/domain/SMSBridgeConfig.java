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
package org.mifos.module.sms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sms_bridge_config")
public class SMSBridgeConfig {

    @Id
    @GeneratedValue
    public Long id;

    public String tenant;
    public String apiKey;
    public String endpoint;
    public String mifosToken;
    public String smsProvider;
    public String smsProviderAccountId;
    public String smsProviderToken;
    public String phoneNo;

    public SMSBridgeConfig() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMifosToken() {
        return mifosToken;
    }

    public void setMifosToken(String mifosToken) {
        this.mifosToken = mifosToken;
    }

    public String getSmsProvider() {
        return smsProvider;
    }

    public void setSmsProvider(String smsProvider) {
        this.smsProvider = smsProvider;
    }

    public String getSmsProviderAccountId() {
        return smsProviderAccountId;
    }

    public void setSmsProviderAccountId(String smsProviderAccountId) {
        this.smsProviderAccountId = smsProviderAccountId;
    }

    public String getSmsProviderToken() {
        return smsProviderToken;
    }

    public void setSmsProviderToken(String smsProviderToken) {
        this.smsProviderToken = smsProviderToken;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
