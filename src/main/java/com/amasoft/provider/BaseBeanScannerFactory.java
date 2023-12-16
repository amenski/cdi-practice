package com.amasoft.provider;

import com.amasoft.ReflectUtils;
import com.amasoft.annotation.marker.Singleton;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

import static org.reflections.scanners.Scanners.TypesAnnotated;

public class BaseBeanScannerFactory {

    private final Reflections reflections;
    private static final BaseBeanProvider BASE_BEAN_PROVIDER = BaseBeanProvider.getInstance();;

    public BaseBeanScannerFactory(String packageToScan) {
        this(packageToScan, "");
    }

    public BaseBeanScannerFactory(String packageToScan, String packageExclude) {
        this(packageToScan, packageExclude, Scanners.values());
    }

    public BaseBeanScannerFactory(String packageToScan, String packageExclude, Scanners[] scanners) {
        reflections = new Reflections(
                new ConfigurationBuilder()
                        .forPackage(packageToScan)
                        .filterInputsBy(new FilterBuilder()
                                .includePackage(packageToScan)
                                .excludePackage(packageExclude))
                        .setScanners(scanners));
        scanAndRegisterSingleton();
    }

    private void scanAndRegisterSingleton() {
        Set<Class<?>> singletons = reflections.get(TypesAnnotated.with(Singleton.class).asClass());
        singletons.forEach(aClass ->
                BASE_BEAN_PROVIDER.register(aClass, ReflectUtils.newInstance(aClass)));
    }
}
