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
