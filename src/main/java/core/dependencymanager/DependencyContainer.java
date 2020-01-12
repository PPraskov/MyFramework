package core.dependencymanager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

final class DependencyContainer {

    private static DependencyContainer instance = null;
    private final AtomicReference<Map<Class, BeanDependency>> beans;
    private final AtomicReference<Map<Class, ComponentDependency>> components;

    private DependencyContainer(Map<Class, BeanDependency> beans,
                                Map<Class, ComponentDependency> components) {
        Map<Class,BeanDependency> tempBeans = Collections.unmodifiableMap(new HashMap<>(beans));
        Map<Class,ComponentDependency> tempComponents = Collections.unmodifiableMap(new HashMap<>(components));

        this.beans = new AtomicReference<>(tempBeans);
        this.components = new AtomicReference<>(tempComponents);

    }

    static synchronized DependencyContainer init(Map<Class, BeanDependency> beans,
                                                 Map<Class, ComponentDependency> components) {
        if (instance == null) {
            instance = new DependencyContainer(beans, components);
        }
        return instance;

    }

    synchronized static DependencyContainer getInstance() {
        return instance;
    }

    Map<Class, BeanDependency> getBeans() {
        return  Collections.unmodifiableMap(new HashMap<>(this.beans.get()));
    }

    Map<Class, ComponentDependency> getComponents() {
        return Collections.unmodifiableMap(new HashMap<>(this.components.get()));
    }

}
