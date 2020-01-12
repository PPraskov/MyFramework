package core.dependencymanager;

import core.repository.RepositoryFactoryImpl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DependencyResolveManager implements DependencyBuilder {

    private final Map<Class, BeanDependency> beans;
    private final Map<Class, ComponentDependency> components;
    private TemporaryBeans temporaryBeansClass;
    private Map<Class, Method> temporaryBeans;
    private Map<Class, Object> instances;
    private Map<Class, DependencyType> dependencyType;

    public DependencyResolveManager() {
        this.instances = new HashMap<>();
        this.beans = DependencyContainer.getInstance().getBeans();
        this.components = DependencyContainer.getInstance().getComponents();
        this.dependencyType = setDependencyTypes();
    }

    private Map<Class, DependencyType> setDependencyTypes() {
        Map<Class, DependencyType> classTypes = new HashMap<>();
        for (Class c : this.beans.keySet()
        ) {
            classTypes.put(c, this.beans.get(c).getDependencyType());
        }
        for (Class c : this.components.keySet()
        ) {
            classTypes.put(c, this.components.get(c).getDependencyType());
        }

        return classTypes;
    }

    @Override
    public Object buildController(Class aClass) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return build(aClass);
    }

    @Override
    public Object buildComponent(Class aClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return build(aClass);
    }

    @Override
    public Object[] buildMethodDependencies(Method m) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Class[] classArr = m.getParameterTypes();
        Object[] objects = new Object[classArr.length];
        for (int i = 0; i < classArr.length; i++) {
            objects[i] = build(classArr[i]);
        }
        return objects;
    }

    private Object build(Class aClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Object o = null;
        if (this.instances.containsKey(aClass)) {
            return this.instances.get(aClass);
        }
//        aClass = checkIfInterface(aClass);
        DependencyType type = getComponentType(aClass);
        switch (type) {
            case BEAN:
                return createBean(aClass, this.beans);
            case REPOSITORY:
                return createRepository(aClass);
            case CONTROLLER:
            case BASIC_COMPONENT:
            case SERVICE:
                Class[] parameterTypes = this.components
                        .get(aClass)
                        .getConstructor()
                        .getParameterTypes();
                Object[] params = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class param = parameterTypes[i];
                    params[i] = build(param);
                }
                o = this.components.get(aClass).getConstructor().newInstance(params);
                if (true){ //TODO
                    this.instances.put(aClass, o);
                }
                break;

        }
        return o;
    }

    private Class checkIfInterface(Class aClass){
        Class clazz = aClass;
        if (aClass.isInterface()){
            for (Class c: this.components.keySet()
            ) {
                if (aClass.isAssignableFrom(c)){
                    clazz = c;
                    break;
                }
            }
        }
        return clazz;
    }

    private DependencyType getComponentType(Class aClass) {
        return this.dependencyType.get(aClass);
    }

    private Object createRepository(Class aClass) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        return RepositoryFactoryImpl.getFactory().createRepository(this.components.get(aClass).getConstructor());
    }
//    Object buildComponent(Class aClass) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
//        Object o = null;
//        if (this.instances.containsKey(aClass)) {
//            return this.instances.get(aClass);
//        } else if (this.defaultBeans.containsKey(aClass)) {
//            return createBean(aClass,this.defaultBeans);
//        } else if (this.applicationBeans.containsKey(aClass)) {
//            return createBean(aClass,this.applicationBeans);
//        } else if (this.temporaryBeans != null && this.temporaryBeans.containsKey(aClass))
//        {
//           return createTempBean(aClass);
//        }else if (this.components.containsKey(aClass)) {
//            Class[] parameterTypes = DependencyContainer
//                    .getInstance()
//                    .getComponents()
//                    .get(aClass)
//                    .getConstructor()
//                    .getParameterTypes();
//            Object[] params = new Object[parameterTypes.length];
//            for (int i = 0; i < parameterTypes.length; i++) {
//                Class param = parameterTypes[i];
//                params[i] = buildComponent(param);
//            }
//            o = DependencyContainer.getInstance().getComponents().get(aClass).getConstructor().newInstance(params);
//            this.instances.put(aClass, o);
//        }

//        return o;

//    }

    private Object createTempBean(Class aClass) throws InvocationTargetException, IllegalAccessException {
        Method m = this.temporaryBeans.get(aClass);
        Object o = temporaryBeansClass;
        return m.invoke(o);
    }

    private Object createBean(Class aClass, Map<Class, BeanDependency> beans) throws
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = beans.get(aClass).getEmptyConstructor();
        Method buildMethod = beans.get(aClass).getBuildMethod();
        Object instance = constructor.newInstance();
        Object o = buildMethod.invoke(instance);
        this.instances.put(aClass, o);
        return o;
    }

}
