package core.repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public interface RepositoryFactory {

    Object createRepository(Class aClass, Constructor constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
