package core.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public final class RepositoryFactoryImpl implements RepositoryFactory {
    private static RepositoryFactoryImpl instance = null;
    private final AtomicReference<Map<String, String>> settings;
    private final AtomicReference<Set<Class>> models;
    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;

    private RepositoryFactoryImpl(Map<String, String> settings, Set<Class> models)  {
        Map<String, String> tempSettings = Collections.unmodifiableMap(new HashMap<>(settings));
        Set<Class> tempModels = Collections.unmodifiableSet(new HashSet<>(models));
        this.settings = new AtomicReference<>(tempSettings);
        this.models = new AtomicReference<>(tempModels);
        this.sessionFactory = initSessionFactory();


    }

    synchronized static void init(Map<String, String> settings, Set<Class> models) {
        if (instance == null) {
            instance = new RepositoryFactoryImpl(settings, models);
        }

    }

    public synchronized static RepositoryFactoryImpl getFactory() {
        return instance;
    }

    private SessionFactory initSessionFactory() {
        if (this.sessionFactory == null) {
            try {
                StandardServiceRegistryBuilder registryBuilder =
                        new StandardServiceRegistryBuilder();

                registryBuilder.applySettings(this.settings.get());

                this.registry = registryBuilder.build();

                MetadataSources sources = new MetadataSources(registry);
                for (Class c : models.get()
                ) {
                    sources.addAnnotatedClass(c);
                }

                Metadata metadata = sources.getMetadataBuilder().build();

                this.sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                System.out.println("SessionFactory creation failed");
                if (this.registry != null) {
                    StandardServiceRegistryBuilder.destroy(this.registry);
                }
            }
        }
        return sessionFactory;
    }

    private SessionFactory getSessionFactory() {
        return initSessionFactory();
    }

    public void shutdown() {
        if (this.registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public Object createRepository(Class aClass, Constructor constructor)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Repository repository = (Repository) constructor.newInstance();
        repository.setSessionFactory(getSessionFactory());
//        Class entityClass = getEntityClass(aClass);
//        repository.setEntityClass(entityClass);
        return repository;
    }

    private Class getEntityClass(Class aClass) {
        TypeVariable typeParameter = aClass.getTypeParameters()[0];
        for (Class c : this.models.get()
        ) {
            if (c.getSimpleName().equals(typeParameter.getName())) {
                return c;
            }
        }
        return null;
    }


}
