package core.dependencymanager;

import core.exception.BeanException;
import core.exception.DependencyException;
import core.repository.RepositoryInitializer;
import core.repository.RepositoryInitializerImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class DependencyInitializeManager implements DependencyInitializer {


    public DependencyInitializeManager() {
    }


    @Override
    public void initialize(String packageName,String corePackageName)
            throws BeanException, DependencyException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {
        Map<Class, BeanDependency> defaultBeans = new BeansInitializer().initializeApplicationBeans(packageName);
//        Map<Class, BeanDependency> applicationBeans = new BeansInitializer().initializeDefaultBeans(corePackageName);
        Map<Class, ComponentDependency> components = new ComponentsInitializer().initializeComponents(packageName);

        Map<Class, BeanDependency> beans = new HashMap<>(defaultBeans);
//        beans.putAll(applicationBeans);


        DependencyContainer.init(beans, components);

        RepositoryInitializer initializer = new RepositoryInitializerImpl();
        initializer.initialize(packageName);
    }
}
