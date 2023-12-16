package com.amasoft.factory;

public interface BeanFactory {

    <T> T newInstance(Class<T> aClass);

    <T> T newSingletonInstance(Class<T> aClass);
}
