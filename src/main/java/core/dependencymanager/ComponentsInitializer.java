package core.dependencymanager;

import core.annotations.dependency.*;
import core.exception.DependencyException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

class ComponentsInitializer {

    ComponentsInitializer() {
    }

    Map<Class, ComponentDependency> initializeComponents(String packageName) throws NoSuchMethodException {
        return setComponents(packageName);
    }

    private Set<Class> scanForTypes(String packageName, Class aClass) {
        return new Reflections(packageName).getTypesAnnotatedWith(aClass);
    }

    private Map<Class, ComponentDependency> setComponents(String packageName) throws DependencyException, NoSuchMethodException {
        Map<Class, ComponentDependency> componentsMap = new HashMap<>();
        Set<Class> components = new HashSet<>();
        components.addAll(scanForTypes(packageName, Component.class));
        components.addAll(scanForTypes(packageName, Service.class));
        components.addAll(scanForTypes(packageName, Controller.class));
        components.addAll(scanForTypes(packageName,Repository.class));
        Reflections reflections = new Reflections(packageName);
        for (Class aClass : components
        ) {
            DependencyType dependencyType = DependencyType.BASIC_COMPONENT;
            if (aClass.isAnnotationPresent(Controller.class)) {
                if (aClass.isAnnotationPresent(Service.class)
                        || aClass.isAnnotationPresent(Repository.class)) {
                    throw new DependencyException("A component can be only a controller, service or repository!");
                }
                dependencyType = DependencyType.CONTROLLER;
            } else if (aClass.isAnnotationPresent(Service.class)) {
                if (aClass.isAnnotationPresent(Controller.class)
                        || aClass.isAnnotationPresent(Repository.class)) {
                    throw new DependencyException("A component can be only a controller, service or repository!");
                }
                dependencyType = DependencyType.SERVICE;
            }
            else if (aClass.isAnnotationPresent(Repository.class)) {
                if (aClass.isAnnotationPresent(Service.class)
                        || aClass.isAnnotationPresent(Controller.class)) {
                    throw new DependencyException("A component can be only a controller, service or repository!");
                }
                dependencyType = DependencyType.REPOSITORY;
            }
            Class subClass = aClass;
            if (aClass.isInterface()) {
                Set<Class> classChildren = reflections.getSubTypesOf(aClass);
                if (classChildren.size() > 1) {
                    throw new DependencyException("Component interface can be implemented in only one class!");
                }
                for (Class c : classChildren
                ) {
                    subClass = c;
                }
            }
            ComponentDependency dependency = getDependency(subClass, dependencyType);
            componentsMap.put(aClass, dependency);
        }
        return componentsMap;
    }


    private ComponentDependency getDependency(Class clazz, DependencyType dependencyType) throws DependencyException, NoSuchMethodException {
        ComponentDependency dependency = getComponentDependency(clazz, dependencyType);
        if (dependency == null) {
            throw new DependencyException("Dependency not set!");
        }
        return dependency;
    }


    private ComponentDependency getComponentDependency(Class theClass, DependencyType dependencyType) throws DependencyException, NoSuchMethodException {
        Constructor[] declaredConstructors = theClass.getDeclaredConstructors();
        Set<Constructor> constructors = Arrays.stream(declaredConstructors)
                .filter(constructor -> constructor.getAnnotation(Autowire.class) != null)
                .collect(Collectors.toSet());
        if (constructors.size() > 1) {
            throw new DependencyException(
                    String.format("Only one constructor can be annotated with %s!", Autowire.class.getSimpleName()));
        } else if (constructors.size() == 0) {
            return new ComponentDependency(theClass.getDeclaredConstructor(), dependencyType);
        }
        Constructor constructor = null;
        for (Constructor con : constructors
        ) {
            constructor = con;
            break;
        }
        if (checkConstructor(constructor, dependencyType)) {
            return new ComponentDependency(constructor, dependencyType);
        }
        return null;
    }

    private boolean checkConstructor(Constructor constructor, DependencyType dependencyType) throws DependencyException {
        Class[] constructorParameterTypes = constructor.getParameterTypes();
        if (constructorParameterTypes.length > 0 && dependencyType == DependencyType.REPOSITORY ){
            return false;
        }
        for (int i = 0; i < constructorParameterTypes.length; i++) {
            Class c = constructorParameterTypes[i];
            if (isComponentIllegal(c, dependencyType)) {
                return false;
            }
        }
        return true;
    }

    private boolean isComponentIllegal(Class c, DependencyType dependencyType) throws DependencyException {
        switch (dependencyType) {
            case CONTROLLER:
                if (c.isAnnotationPresent(Repository.class)) {
                    throw new DependencyException("Controller cannot directly access a repository!");
                }
                if (c.isAnnotationPresent(Controller.class)) {
                    throw new DependencyException("Controller cannot be created in another controller!");
                }
                return false;

            case SERVICE:
            case BASIC_COMPONENT:
                if (c.isAnnotationPresent(Controller.class)) {
                    throw new DependencyException("Controller cannot be created in a component or service!");
                }
                return false;
            default:
                return true;
        }
    }
}