package com.amasoft.provider;

public interface BeanProvider {

    <T> void register(Class<T> aClass, Object instance);

    void unregister(Class<?> aClass);

    void clearRegistry();

    Object getInstanceOfType(Class<?> aClass);
}
