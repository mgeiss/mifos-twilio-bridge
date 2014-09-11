package org.mifos.module.twilio.provider;

public interface SMSGateway {
    boolean sendMessage(final String mobileNo, final String message);
}
