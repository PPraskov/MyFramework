package core.repository;

import java.lang.reflect.InvocationTargetException;

public interface RepositoryInitializer {

    void initialize(String packageName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException;
}
