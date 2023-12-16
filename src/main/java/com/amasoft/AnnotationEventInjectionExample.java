package com.amasoft;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@interface MyEvent {
}

class EventSource {

    private final List<Object> listeners = new ArrayList<>();

    public void addListener(Object listener) {
        listeners.add(listener);
    }

    @MyEvent
    public void fireEvent() {
        System.out.println("Event fired from EventSource");
        // Notify listeners here
        notifyListeners();
    }

    private void notifyListeners() {
//        List<Object> listenersToNotify = getListeners();
        for (Object listener : listeners) {
            if (listener instanceof MyListener) {
                ((MyListener) listener).handleEvent();
            }
        }
    }

    private List<Object> getListeners() {
        List<Object> annotatedListeners = new ArrayList<>();
        Method[] methods = getClass().getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyEvent.class)) {
                try {
                    List<Object> methodListeners = (List<Object>) method.invoke(this);
                    if (methodListeners != null) {
                        annotatedListeners.addAll(methodListeners);
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Handle exceptions appropriately
                }
            }
        }
        return annotatedListeners;
    }
}

class MyListener {
    public void handleEvent() {
        System.out.println("Event handled by MyListener");
    }
}

public class AnnotationEventInjectionExample {

    public static void main(String[] args) {
        EventSource eventSource = new EventSource();

        // Add a listener
        MyListener myListener = new MyListener();
        eventSource.addListener(myListener);

        // Trigger the annotated event
        eventSource.fireEvent();
    }
}
