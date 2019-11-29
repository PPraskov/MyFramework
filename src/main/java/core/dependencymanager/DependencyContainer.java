package core.dependencymanager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

final class DependencyContainer {

    private static DependencyContainer instance = null;
    private final AtomicReference<Map<Class, BeanDependency>> defaultBeans;
    private final AtomicReference<Map<Class, BeanDependency>> applicationBeans;
    private final AtomicReference<Map<Class, ComponentDependency>> components;

    private DependencyContainer(AtomicReference<Map<Class, BeanDependency>> defaultBeans,
                                AtomicReference<Map<Class, BeanDependency>> applicationBeans,
                                AtomicReference<Map<Class, ComponentDependency>> components) {
        this.defaultBeans = defaultBeans;
        this.applicationBeans = applicationBeans;
        this.components = components;
    }

    static synchronized DependencyContainer init(AtomicReference<Map<Class, BeanDependency>> defaultBeans,
                                                 AtomicReference<Map<Class, BeanDependency>> applicationBeans,
                                                 AtomicReference<Map<Class, ComponentDependency>> components) {
        if (instance == null) {
            instance = new DependencyContainer(defaultBeans, applicationBeans, components);
        }
        return instance;

    }

    synchronized static DependencyContainer getInstance() {
        return instance;
    }

    Map<Class, BeanDependency> getDefaultBeans() {
        return new HashMap<>(this.defaultBeans.get());
    }

    Map<Class, BeanDependency> getApplicationBeans() {
        return new HashMap<>(this.applicationBeans.get());
    }

    Map<Class, ComponentDependency> getComponents() {
        return new HashMap<>(this.components.get());
    }

}
