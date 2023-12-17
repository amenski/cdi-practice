package com.amasoft.event;

import java.time.Instant;

public abstract class ApplicationEvent {

    protected String name;
    protected long firedOn = Instant.now().toEpochMilli();

    public void setName(String name) {
        this.name = name;
    }
}
