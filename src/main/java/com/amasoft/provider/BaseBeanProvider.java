package com.amasoft.provider;

import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseBeanProvider {

    private static final BaseBeanProvider instance = new BaseBeanProvider();

    public static BaseBeanProvider getInstance() {
        return instance;
    }

    private static final Map<String, Object> instanceRegistry = new ConcurrentHashMap<String, Object>();

    public <T> void register(Class<T> aClass, Object instance) {
        Preconditions.checkArgument(aClass.isAssignableFrom(instance.getClass()));
        instanceRegistry.put(aClass.getName(), instance);
    }

    public void unregister(Class<?> aClass) {
        instanceRegistry.remove(aClass.getName());
    }

    public void clearRegistry() {
        instanceRegistry.clear();
    }

    public Object getInstanceOfType(Class<?> aClass) {
        return instanceRegistry.get(aClass.getName());
    }
}
