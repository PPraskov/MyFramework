package core.dependencymanager;

import core.annotations.configuration.BeanConfiguration;
import core.annotations.dependency.Bean;
import core.exception.BeanException;
import core.exception.DependencyException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

class BeansInitializer {

    BeansInitializer() {
    }

    Map<Class, BeanDependency> initializeApplicationBeans(String packageName) {
        return setBeans(packageName);
    }

    Map<Class, BeanDependency> initializeDefaultBeans(String packageName) {
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
                    BeanDependency bd = new BeanDependency(DependencyType.BEAN, constructors.get(0), createMethod);
                    beansMap.put(aClass, bd);
                }
            }
        }
        return beansMap;
    }
}
