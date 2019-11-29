package core.dependencymanager;

import core.execution.ExtendedHttpExchange;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface DependencyBuilder {

    void init(ExtendedHttpExchange exchange);

    Object buildController(Class aClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;

    Object[] buildMethodDependencies(Method m) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
