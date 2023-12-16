package com.amasoft.provider;

import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public final class CdiSingletonProvider {

    private static CdiSingletonProvider instance = new CdiSingletonProvider();

    public static CdiSingletonProvider getInstance() {
        return instance;
    }

    public static void setInstance(CdiSingletonProvider instance) {
        CdiSingletonProvider.instance = instance;
    }

    private final Map<String, Object> instanceRegistry = new ConcurrentHashMap<String, Object>();

    public <T> void register(Class<T> componentClass, Object instance) {
        Preconditions.checkArgument(componentClass.isAssignableFrom(instance.getClass()));
        instanceRegistry.put(componentClass.getName(), instance);
    }

    public void unregister(Class<?> componentClass) {
        instanceRegistry.remove(componentClass.getName());
    }

    public void clearRegistry() {
        instanceRegistry.clear();
    }

    public <T> T get(Class<T> componentClass) {
        Object instanceFromRegistry = instanceRegistry.get(componentClass.getName());
        if(instanceFromRegistry == null) {
            try {
                instanceFromRegistry = SingletonProvider.getInstance().newInstance(componentClass);
                register(componentClass, instanceFromRegistry);
                return componentClass.cast(instanceFromRegistry);
            } catch(Exception ignored) { }
        }
        return null;
    }
}
