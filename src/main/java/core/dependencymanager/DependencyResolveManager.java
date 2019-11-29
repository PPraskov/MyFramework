package core.dependencymanager;

import core.execution.ExtendedHttpExchange;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DependencyResolveManager implements DependencyBuilder {

    private final Map<Class, BeanDependency> defaultBeans;
    private final Map<Class, BeanDependency> applicationBeans;
    private final Map<Class, ComponentDependency> components;
    private TemporaryBeans temporaryBeansClass;
    private Map<Class, Method> temporaryBeans;
    private Map<Class, Object> instances;

    public DependencyResolveManager() {
        this.instances = new HashMap<>();
        this.defaultBeans = DependencyContainer.getInstance().getDefaultBeans();
        this.applicationBeans = DependencyContainer.getInstance().getApplicationBeans();
        this.components = DependencyContainer.getInstance().getComponents();
    }

    Object buildComponent(Class aClass) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Object o = null;
        if (this.instances.containsKey(aClass)) {
            return this.instances.get(aClass);
        } else if (this.defaultBeans.containsKey(aClass)) {
            return createBean(aClass,this.defaultBeans);
        } else if (this.applicationBeans.containsKey(aClass)) {
            return createBean(aClass,this.applicationBeans);
        } else if (this.temporaryBeans != null && this.temporaryBeans.containsKey(aClass))
        {
           return createTempBean(aClass);
        }else if (this.components.containsKey(aClass)) {
            Class[] parameterTypes = DependencyContainer
                    .getInstance()
                    .getComponents()
                    .get(aClass)
                    .getConstructor()
                    .getParameterTypes();
            Object[] params = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                Class param = parameterTypes[i];
                params[i] = buildComponent(param);
            }
            o = DependencyContainer.getInstance().getComponents().get(aClass).getConstructor().newInstance(params);
            this.instances.put(aClass, o);
        }
        return o;
    }

    @Override
    public Object buildController(Class aClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        return  buildComponent(aClass);
    }

    @Override
    public void init(ExtendedHttpExchange exchange) {
        settingUpTemporaryBeans(exchange);
    }

    @Override
    public Object[] buildMethodDependencies(Method m) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class[] classArr = m.getParameterTypes();
        Object[] objects = new Object[classArr.length];
        for (int i = 0; i < classArr.length; i++) {
            objects[i] = buildComponent(classArr[i]);
        }
        return objects;
    }

    private Object createTempBean(Class aClass) throws InvocationTargetException, IllegalAccessException {
        Method m = this.temporaryBeans.get(aClass);
        Object o = temporaryBeansClass;
        return m.invoke(o);
    }

    private Object createBean(Class aClass,Map<Class,BeanDependency> beans) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor constructor = beans.get(aClass).getEmptyConstructor();
        Method buildMethod = beans.get(aClass).getBuildMethod();
        Object instance = constructor.newInstance();
        Object o = buildMethod.invoke(instance);
        this.instances.put(aClass, o);
        return o;
    }


    private void settingUpTemporaryBeans(ExtendedHttpExchange exchange) {
        this.temporaryBeansClass = new TemporaryBeans(exchange);
        Method[] declaredMethods = this.temporaryBeansClass.getClass().getDeclaredMethods();
        Map<Class, Method> tbMap = new HashMap<>();
        for (int i = 0; i < declaredMethods.length; i++) {
            Method m = declaredMethods[i];
            Class<?> returnType = m.getReturnType();
            tbMap.put(returnType, m);
        }
        this.temporaryBeans = tbMap;
    }
}
