package com.amasoft.observable;

import com.amasoft.event.*;

import java.lang.reflect.Method;
import java.util.function.Predicate;

public interface Subject<E extends ApplicationEvent> {

    void addListener(ApplicationListener<E> listener);

    void removeListener(ApplicationListener<E> listener);

    void removeAllListeners();

    void notifyListeners();

    int getListenerSize();
}
