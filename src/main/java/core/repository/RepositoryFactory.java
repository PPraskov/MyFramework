package core.repository;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public interface RepositoryFactory {

    Object createRepository(Constructor constructor) throws IllegalAccessException, InvocationTargetException, InstantiationException;
}
