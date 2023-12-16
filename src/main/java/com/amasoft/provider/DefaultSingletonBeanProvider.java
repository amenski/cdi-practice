package com.amasoft.provider;

import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultSingletonBeanProvider implements BeanProvider {

    private static final DefaultSingletonBeanProvider instance = new DefaultSingletonBeanProvider();

    public static DefaultSingletonBeanProvider getInstance() {
        return instance;
    }

    private static final Map<String, Object> instanceRegistry = new ConcurrentHashMap<String, Object>();

    @Override
    public <T> void register(Class<T> aClass, Object instance) {
        Preconditions.checkArgument(aClass.isAssignableFrom(instance.getClass()));
        instanceRegistry.put(aClass.getName(), instance);
    }

    @Override
    public void unregister(Class<?> aClass) {
        instanceRegistry.remove(aClass.getName());
    }

    @Override
    public void clearRegistry() {
        instanceRegistry.clear();
    }

    @Override
    public Object getInstanceOfType(Class<?> aClass) {
        return instanceRegistry.get(aClass.getName());
    }
}
