package core.http.controller;

import core.exception.HttpException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class ControllerManager implements HttpControllerFinder {

    private Class controllerClass;
    private Method methodToInvoke;

    public ControllerManager() {
    }

    @Override
    public void findController(String path, String method, String paramsToBeSorted) throws HttpException {
        Map<String, ControllerData> controllers = ControllerContainer.getInstance().getControllers();
        ControllerData controllerData = controllers.get(path);
        if (controllerData == null) {
            throwException();
        }
        Map<String, Method> methodMap = controllerData.getControllerMethods().get(method);
        if (methodMap == null) {
            throwException();
        }
        String params = sortParams(paramsToBeSorted);
        Method m = null;
        String[] paramsArr = params.split(",");
        int bestMatch = 0;
        for (String key : methodMap.keySet()
        ) {
            int currentBest = 0;
            String[] keyArr = key.split(",");
            for (int i = 0; i < keyArr.length; i++) {
                String val = keyArr[i];
                for (int j = 0; j < paramsArr.length; j++) {
                    String var = paramsArr[j];
                    if (val.equals(var)) {
                        currentBest++;
                    }
                }
            }
            if (currentBest > bestMatch) {
                bestMatch = currentBest;
                m = methodMap.get(key);
            }
        }
        if (bestMatch == 0) {
            m = methodMap.get("");
        }
        if (m == null) {
            throwException();
        }
        Class aClass = controllerData.getaClass();
        settingControllerClassAndMethod(aClass, m);

    }

    @Override
    public Class getControllerClazz() {
        return this.controllerClass;
    }

    @Override
    public Method getControllerMethod() {
        return this.methodToInvoke;
    }

    private void settingControllerClassAndMethod(Class aClass, Method m) {
        this.controllerClass = aClass;
        this.methodToInvoke = m;
    }

    private String sortParams(String paramsToBeSorted) {
        if (paramsToBeSorted.isEmpty()) {
            return paramsToBeSorted;
        }
        return String.join(",",
                Arrays.stream(paramsToBeSorted.trim().split(",")).sorted(String::compareTo).collect(Collectors.toList()));
    }

    private void throwException() throws HttpException {
        throw new HttpException(0, "No such service provided");
    }
}
