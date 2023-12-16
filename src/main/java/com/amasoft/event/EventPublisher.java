package com.amasoft.event;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;


public class EventPublisher {

    public void fireEvent(Set<ListenerMethod> listeners, Object event) throws InvocationTargetException, IllegalAccessException {
        if (listeners == null) return;
        for (ListenerMethod method : listeners) {
            if (method.getEventType().isAssignableFrom(event.getClass())) {
                method.invokeMethod(event); // synchronized
            }
        }
    }

}
