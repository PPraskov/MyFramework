package core.execution.http.controller;

import core.annotations.dependency.Component;
import core.annotations.dependency.Controller;
import core.annotations.httpmapping.httprequesttype.MethodMapping;
import core.exception.ControllerException;
import core.exception.DependencyException;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public final class ControllerInitializer implements HttpControllerInitializer {
    @Override
    public void initialize(String packageName) {
        ControllerContainer.init(mapControllers(packageName));
    }

    private Map<String, ControllerData> mapControllers(String packageName){
        Reflections reflections = new Reflections(packageName);
        Set<Class<?>> controllers = reflections.getTypesAnnotatedWith(Controller.class);
        Map<String, ControllerData> returnControllers = new HashMap<>();
        for (Class clazz : controllers
        ) {
            Annotation[] annotations = clazz.getDeclaredAnnotations();
            if (Arrays.stream(annotations)
                    .filter(annotation ->
                            annotation.annotationType().equals(Component.class))
                    .collect(Collectors.toSet()).isEmpty()) {
                throw new DependencyException(String.format("%s must also be annotated with %s", Controller.class, Component.class));
            }
            Controller controllerAnnotation = (Controller) clazz.getAnnotation(Controller.class);
            String mapping = controllerAnnotation.mapping();
            Method[] methods = clazz.getDeclaredMethods();
            Map<String,Map<String,Method>> controllerMethods = new HashMap<>();
            for (Method m : methods
            ) {
                MethodMapping methodMapping = m.getAnnotation(MethodMapping.class);
                if (methodMapping == null) {
                    throw new ControllerException(String.format("%s in %s must have %s"
                            , m.getName(), clazz.getSimpleName(), MethodMapping.class.getSimpleName()));
                }
                String httpMethod = methodMapping.method().toString().toUpperCase();
                if  (!controllerMethods.containsKey(httpMethod)){
                    controllerMethods.put(httpMethod.toUpperCase(),new HashMap<>());
                }
                Map<String, Method> paramsAndMethods = controllerMethods.get(httpMethod);
                String params = paramGetter(methodMapping.params());
                if (paramsAndMethods.containsKey(params)){
                    throw new ControllerException(String.format("%s parameters in more than one method!",params));
                }
                paramsAndMethods.put(params,m);

                //TODO
            }
            ControllerData data = new ControllerData(clazz,controllerMethods);
            returnControllers.put(mapping,data);
        }
        return returnControllers;
    }

    private String paramGetter(String params){
        if (params.trim().isEmpty()){
            return "";
        }
        return String.join(",",
                Arrays.stream(params.trim().split(",")).sorted(String::compareTo).collect(Collectors.toList()));
    }
}
