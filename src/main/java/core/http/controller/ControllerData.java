package core.http.controller;

import java.lang.reflect.Method;
import java.util.Map;

final class ControllerData {

    private final Class aClass;
    private final Map<String, Map<String, Method>> controllerMethods;

    ControllerData(Class aClass, Map<String, Map<String, Method>> controllerMethods) {
        this.aClass = aClass;
        this.controllerMethods = controllerMethods;
    }

    Class getaClass() {
        return aClass;
    }

    Map<String, Map<String, Method>> getControllerMethods() {
        return controllerMethods;
    }
}
