package core.dependencymanager;

import java.lang.reflect.Method;
import java.util.List;

class ControllerMethodDetails {

    private final Method method;
    private final String httpMethod;
    private final List<String> queryParams;
    private final Class controller;
    private final String controllerPath;

    ControllerMethodDetails(Method method, String httpMethod,
                            Class controller, String controllerPath, List<String> queryParams) {
        this.method = method;
        this.httpMethod = httpMethod;
        this.controller = controller;
        this.controllerPath = controllerPath;
        this.queryParams = queryParams;
    }

    Class getController(){ return controller;}

    String getControllerPath() {
        return controllerPath;
    }

    Method getMethod() {
        return method;
    }

    List<String> getQueryParams() {
        return queryParams;
    }

    String getHttpMethod() {
        return httpMethod;
    }

}
