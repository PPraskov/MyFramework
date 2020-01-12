package core.repository;

import core.annotations.configuration.ConfigurationMethod;
import core.annotations.configuration.EnableHibernate;
import org.reflections.Reflections;

import javax.persistence.Entity;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class RepositoryInitializerImpl implements RepositoryInitializer {

    public RepositoryInitializerImpl() {
    }

    @Override
    public void initialize(String packageName) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Map<String, String> hibernateSettings = getHibernateSettings(packageName);
        Set<Class> models = getHibernateModels(packageName);
        RepositoryFactoryImpl.init(hibernateSettings,models);
    }


    private Set<Class> getHibernateModels(String packageName) {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Entity.class);
        return new HashSet<>(typesAnnotatedWith);
    }

    private Map<String, String> getHibernateSettings(String packageName) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(EnableHibernate.class);
        Map<String, String> settings = new HashMap<>();
        for (Class c : classes
        ) {
            Method[] methods = c.getDeclaredMethods();
            for (Method m : methods
            ) {
                if(m.isAnnotationPresent(ConfigurationMethod.class)){
                    Constructor constructor = c.getDeclaredConstructor();
                    Object o = constructor.newInstance();
                    settings = (Map<String, String>) m.invoke(o);
                }
            }
        }
        return settings;
    }
}
