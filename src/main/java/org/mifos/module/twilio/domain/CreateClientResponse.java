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
package org.mifos.module.twilio.domain;

public class CreateClientResponse {

    private long officeId;
    private long clientId;
    private long resourceIs;
    private long savingsId;

    public CreateClientResponse() {
        super();
    }

    public long getOfficeId() {
        return officeId;
    }

    public void setOfficeId(long officeId) {
        this.officeId = officeId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getResourceIs() {
        return resourceIs;
    }

    public void setResourceIs(long resourceIs) {
        this.resourceIs = resourceIs;
    }

    public long getSavingsId() {
        return savingsId;
    }

    public void setSavingsId(long savingsId) {
        this.savingsId = savingsId;
    }
}
