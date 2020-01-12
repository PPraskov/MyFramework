package core.execution.http.controller;

import java.lang.reflect.Method;

public interface HttpControllerFinder {

    void findController(String path, String method , String paramsToBeSorted);
    Class getControllerClazz();
    Method getControllerMethod();
}
