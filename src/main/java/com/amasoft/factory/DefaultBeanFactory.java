package com.amasoft.factory;

import com.amasoft.ReflectUtils;
import com.amasoft.provider.DefaultSingletonBeanProvider;
import com.amasoft.provider.BeanProvider;

public final class DefaultBeanFactory implements BeanFactory {

    private BeanProvider beanProvider = new DefaultSingletonBeanProvider();
    private static final DefaultBeanFactory instance = new DefaultBeanFactory();

    public static DefaultBeanFactory getInstance() {
        return instance;
    }

    public void setBeanProvider(BeanProvider beanProvider) {
        this.beanProvider = beanProvider;
    }

    public <T> T newInstance(Class<T> aClass) {
        return (T) ReflectUtils.newInstance(aClass);
    }

    public <T> T newSingletonInstance(Class<T> aClass) {
        Object existingInstance = beanProvider.getInstanceOfType(aClass);
        if (null != existingInstance) {
            return (T) existingInstance;
        }
        T newInstance = ReflectUtils.newInstance(aClass);
        beanProvider.register(aClass, newInstance);
        return newInstance;
    }
}
