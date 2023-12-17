package com.amasoft.observable;


import com.amasoft.event.ApplicationEvent;
import com.amasoft.event.ApplicationListener;

import java.util.LinkedHashSet;

/**
 * In observer pattern, the object that watch on the state of another object
 * are called Observer and the object that is being watched is called Subject.
 *
 * Subject contains a list of observers to notify of any change in its state,
 * so it should provide methods using which observers can register and unregister themselves.
 */
public abstract class AbstractSubject<E extends ApplicationEvent> implements Subject<E> {

    private E event;

    private final LinkedHashSet<ApplicationListener<E>> listeners = new LinkedHashSet<>();

    public void updateEvent(E event) {
        this.event = event;
        notifyListeners();
    }

    @Override
    public void addListener(ApplicationListener<E> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(ApplicationListener<E> listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeAllListeners() {
        listeners.clear();
    }

    @Override
    public void notifyListeners() {
        listeners.forEach(listener -> listener.onApplicationEvent(this.event));
    }

    @Override
    public int getListenerSize() {
        return listeners.size();
    }
}
