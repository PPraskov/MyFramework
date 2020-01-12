package core.dependencymanager;

import core.execution.http.HttpRequestThreadLocal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface DependencyBuilder {

    Object buildComponent(Class aClass) throws IllegalAccessException, InvocationTargetException, InstantiationException;

    Object[] buildControllerMethodDependencies(Method m, HttpRequestThreadLocal httpRequestThreadLocal) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
}
