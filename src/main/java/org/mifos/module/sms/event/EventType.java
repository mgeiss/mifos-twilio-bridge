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
package org.mifos.module.sms.event;

import org.mifos.module.sms.exception.UnknownEventTypeException;

public enum EventType {

    CREATE_CLIENT("client", "create"),
    LOAN_REPAYMENT("loan", "repayment"),
    SEND_SMS("sms", "send"),
    LOAN_REPAYMENT_SMS_REMINDE("SCHEDULE","EXECUTEJOB"),
    LOAN_FIRST_AND_SECOND_OVERDUE_REPAYMENT_REMINDER("FIRSTOVERDUE","SECONDOVERDUE"),
    LOAN_THIRD_AND_FOURTH_OVERDUE_REPAYMENT_REMINDER("THIRDOVERDUE","FOURTHOVERDUE"),
    BULK_SMS_SEND("SCHEDULER","EXECUTEJOB");

    private String entity;
    private String action;

    private EventType(final String entity, final String action) {
        this.entity = entity;
        this.action = action;
    }

    public static EventType get(final String entity, final String action) {
        for (final EventType et : values()) {
            if (et.entity.equalsIgnoreCase(entity)
                    && et.action.equalsIgnoreCase(action)) {
                return et;
            }
        }

        throw new UnknownEventTypeException(entity, action);
    }
}
