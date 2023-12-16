package com.amasoft.observable;


import com.amasoft.ReflectUtils;
import com.amasoft.annotation.event.CustomEvent;
import com.amasoft.event.EventDispatcher;
import com.amasoft.event.ListenerMethod;
import com.amasoft.provider.DefaultSingletonBeanProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.function.Predicate;

/**
 * In observer pattern, the object that watch on the state of another object
 * are called Observer and the object that is being watched is called Subject.
 *
 * Subject contains a list of observers to notify of any change in its state,
 * so it should provide methods using which observers can register and unregister themselves.
 */
public abstract class AbstractSubject implements Subject {

    private boolean notifiable = true;

    private final EventDispatcher eventDispatcher;

    private LinkedHashSet<ListenerMethod> listeners = null;

    protected AbstractSubject() {
        eventDispatcher = (EventDispatcher) DefaultSingletonBeanProvider
                .getInstance()
                .getInstanceOfType(EventDispatcher.class);

    }

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
        if (listeners == null) {
            listeners = new LinkedHashSet<>();
        }
        listeners.add(new ListenerMethod(eventType, listener, method));
    }

    @Override
    public void addListener(Class<?> eventType, Object listener, String methodName) {
        Method method = ReflectUtils.getFirstMethodOfName(listener.getClass(), methodName);
        addListener(eventType, listener, method);
    }

    public void removeListener(Predicate<ListenerMethod> filter) {
        if (listeners != null) {
            listeners.removeIf(filter);
        }
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener, Method method) {
        if (listeners != null) {
            listeners.removeIf(listenerMethod -> listenerMethod.matches(eventType, listener, method));
        }
    }

    @Override
    public int getListenerSize() {
        return listeners != null ? listeners.size() : 0;
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener, String methodName) {
        Method method = ReflectUtils.getFirstMethodOfName(listener.getClass(), methodName);
        removeListener(eventType, listener, method);
    }

    @Override
    public void removeListener(Class<?> eventType, Object listener) {
        if (listeners != null) {
            listeners.removeIf(listenerMethod -> listenerMethod.matches(eventType, listener));
        }
    }

    @Override
    public void removeAllListeners() {
       listeners = null;
    }

    @Override
    public void fireEvent(CustomEvent event) {
        if (eventDispatcher != null && isNotifiable()) {
            try {
                eventDispatcher.fireEvent(listeners, event);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
