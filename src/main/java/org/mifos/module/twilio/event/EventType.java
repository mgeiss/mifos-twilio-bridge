package org.mifos.module.twilio.event;

public enum EventType {

    CREATE_CLIENT("client", "create");

    private String entity;
    private String action;

    private EventType(final String entity, final String action) {
        this.entity = entity;
        this.action = action;
    }

    public static EventType get(final String entity, final String action) {
        for (final EventType et : values()) {
            if (et.entity.equals(entity) && et.action.equals(action)) {
                return et;
            }
        }

        throw new IllegalArgumentException("Unknown event type!");
    }
}
