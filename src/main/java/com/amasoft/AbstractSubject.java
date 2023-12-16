package com.amasoft;


import com.amasoft.provider.BaseBeanProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public abstract class AbstractSubject implements Observable {

    private boolean notifiable = true;

    private CustomEventDispatcher eventDispatcher;

    @Override
    public boolean isNotifiable() {
        return notifiable;
    }

    @Override
    public void setNotifiable(boolean notifiable) {
        this.notifiable = notifiable;
    }

    @Override
    public void addListener(Class<?> eventType, Object listener, Method method) {
        if (eventDispatcher == null) {
            eventDispatcher = (CustomEventDispatcher) BaseBeanProvider.getInstance().getInstanceOfType(CustomEventDispatcher.class);
        }
        eventDispatcher.register(eventType, listener, method);
    }

    @Override
    public void addListener(Class<?> eventType, Object listener, String methodName) {
        Method method = ReflectUtils.getFirstMethodOfName(listener.getClass(), methodName);
        addListener(eventType, listener, method);
    }

    public void removeListener(Predicate<ListenerMethod> predicate) {
        if (eventDispatcher != null) {
            eventDispatcher.unregister(predicate);
        }
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener, Method method) {
        if (eventDispatcher != null) {
            eventDispatcher.unregister(eventType, listener, method);
        }
    }

    @Override
    public int getListenerSize() {
        return eventDispatcher!=null ? eventDispatcher.getListenerSize() : 0;
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener, String methodName) {
        Method method = ReflectUtils.getFirstMethodOfName(listener.getClass(), methodName);
        removeListener(eventType, listener, method);
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener) {
        if (eventDispatcher != null) {
            eventDispatcher.unregister(eventType, listener);
        }
    }

    @Override
    public void removeAllListeners() {
        if (eventDispatcher != null)
            eventDispatcher.unregisterAll();
    }

    @Override
    public void fireEvent(CustomEvent event) {
        if (eventDispatcher != null && isNotifiable()) {
            try {
                eventDispatcher.fireEvent(event);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
