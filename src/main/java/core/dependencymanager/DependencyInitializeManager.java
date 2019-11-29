package core.dependencymanager;

import core.annotations.configuration.BeanConfiguration;
import core.annotations.dependency.Autowire;
import core.annotations.dependency.Bean;
import core.annotations.dependency.Component;
import core.exception.BeanException;
import core.exception.DependencyException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public final class DependencyInitializeManager implements DependencyInitializer {


    public DependencyInitializeManager() {
    }


    @Override
    public void initialize() throws BeanException, DependencyException {
        Map<Class, BeanDependency> defaultBeans = initializeDefaultBeans();
        Map<Class, BeanDependency> applicationBeans = initializeApplicationBeans();
        Map<Class, ComponentDependency> components = initializeComponents();

        AtomicReference<Map<Class, BeanDependency>> atomicDefaultBeans = new AtomicReference<>();
        AtomicReference<Map<Class, BeanDependency>> atomicApplicationBeans = new AtomicReference<>();
        AtomicReference<Map<Class, ComponentDependency>> atomicComponents = new AtomicReference<>();

        atomicDefaultBeans.set(defaultBeans);
        atomicApplicationBeans.set(applicationBeans);
        atomicComponents.set(components);

        DependencyContainer.init(atomicDefaultBeans, atomicApplicationBeans, atomicComponents);
    }


    private Map<Class, ComponentDependency> initializeComponents() {
        String packageName = "app";
        return setComponents(packageName);
    }

    private Map<Class, ComponentDependency> setComponents(String packageName) throws DependencyException {
        Map<Class, ComponentDependency> componentsMap = new HashMap<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> components = reflections.getTypesAnnotatedWith(Component.class);
        for (Class aClass : components
        ) {
            Class subClass = aClass;
            if (aClass.isInterface()) {
                Set classChildren = reflections.getSubTypesOf(aClass);
                if (classChildren.size() > 1) {
                    throw new DependencyException("Component interface can be implemented in only one class!");
                }
                for (Object c : classChildren
                ) {
                    subClass = (Class) c;
                }
            }
            ComponentDependency dependency = checkDependency(subClass);
            componentsMap.put(subClass, dependency);
        }
        return componentsMap;
    }

    private ComponentDependency checkDependency(Class clazz) throws DependencyException {
        ComponentDependency dependency = getComponentDependency(clazz);
        if (dependency == null) {
            throw new DependencyException("Dependency not set!");
        }
        return dependency;
    }

    private ComponentDependency getComponentDependency(Class theClass) throws DependencyException {
        Constructor[] constructorss = theClass.getDeclaredConstructors();
        Set<Constructor> constructors = Arrays.stream(constructorss)
                .filter(constructor -> constructor.getAnnotation(Autowire.class) != null)
                .collect(Collectors.toSet());
        if (constructors.size() > 1) {
            throw new DependencyException(
                    String.format("Only one constructor can be annotated with %s!", Autowire.class.getSimpleName()));
        } else if (constructors.size() == 0) {
            throw new DependencyException(
                    String.format("No constructor annotated with %s!", Autowire.class.getSimpleName()));
        } else {
            for (Constructor constructor : constructors
            ) {
                return new ComponentDependency(constructor);
            }
        }
        return null;
    }


    private Map<Class, BeanDependency> initializeApplicationBeans() {
        String packageName = "app";
        return setBeans(packageName);
    }

    private Map<Class, BeanDependency> initializeDefaultBeans() {
        String packageName = "core";
        return setBeans(packageName);
    }

    private Map<Class, BeanDependency> setBeans(String packageName) throws BeanException, DependencyException {
        Map<Class, BeanDependency> beansMap = new HashMap<>();
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> beans = reflections.getTypesAnnotatedWith(BeanConfiguration.class);
        if (beans.size() > 1) {
            throw new DependencyException(
                    String.format("Only one configuration file allowed! Found %d", beans.size()));
        } else if (beans.size() == 0) {
            throw new DependencyException("No configuration file found!");
        } else {

            for (Class configFile : beans
            ) {
                List<Constructor> constructors = Arrays.stream(configFile.getDeclaredConstructors())
                        .filter(constructor -> constructor.getParameterCount() == 0)
                        .collect(Collectors.toList());
                if (constructors.isEmpty()) {
                    throw new DependencyException(String.format("Empty constructor for %s not provided!", configFile.getSimpleName()));
                }
                Set<Method> beanMethods = Arrays.stream(configFile.getDeclaredMethods())
                        .filter(method -> method.getAnnotation(Bean.class) != null).collect(Collectors.toSet());
                for (Method createMethod : beanMethods
                ) {
                    if (createMethod.getParameterTypes().length != 0) {
                        throw new BeanException("No input parameters allowed in beans!");
                    }
                    Class aClass = createMethod.getReturnType();
                    BeanDependency bd = new BeanDependency(configFile, constructors.get(0), createMethod);
                    beansMap.put(aClass, bd);
                }
            }
        }
        return beansMap;
    }
}
