package com.amasoft;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.function.Predicate;


public class CustomEventDispatcher {

    private LinkedHashSet<ListenerMethod> listeners = null;

    public void register(Class<?> eventType, Object listener, Method method) {
        if (listeners == null) {
            listeners = new LinkedHashSet<>();
        }
        listeners.add(new ListenerMethod(eventType, listener, method));
    }

    public void unregister(Predicate<ListenerMethod> filter) {
        if (listeners != null) {
            listeners.removeIf(filter);
        }
    }

    public void unregister(final Class<?> eventType, final Object target) {
        if (listeners != null) {
            listeners.removeIf(listenerMethod -> listenerMethod.matches(eventType, target));
        }
    }

    public void unregister(final Class<?> eventType, final Object target, final Method method) {
        if (listeners != null) {
            listeners.removeIf(listenerMethod -> listenerMethod.matches(eventType, target, method));
        }
    }

    public void unregisterAll() {
        listeners = null;
    }

    public void fireEvent(Object event) throws InvocationTargetException, IllegalAccessException {
        if (listeners == null) return;
        for (ListenerMethod method : listeners) {
            if (method.getEventType().isAssignableFrom(event.getClass())) {
                method.invokeMethod(event);
            }
        }
    }

    public int getListenerSize() {
        return listeners!=null ? listeners.size() : 0;
    }
}
