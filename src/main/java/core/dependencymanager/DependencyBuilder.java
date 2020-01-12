package core.dependencymanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface DependencyBuilder {

    Object buildComponent(Class aClass) throws IllegalAccessException, InvocationTargetException, InstantiationException;

    Object buildController(Class aClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    Object[] buildMethodDependencies(Method m) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
