package core.dependencymanager;

import core.exception.BeanException;
import core.exception.DependencyException;

import java.lang.reflect.InvocationTargetException;

public interface DependencyInitializer {

    void initialize(String packageName, String corePackageName) throws BeanException, DependencyException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;
}
