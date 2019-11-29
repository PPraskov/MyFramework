package core.utill;

import java.lang.reflect.Method;

public class MethodAndClassCarrier {
    private final Method method;
    private final Class clazz;

    public MethodAndClassCarrier(Method method, Class clazz) {
        this.method = method;
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public Class getClazz() {
        return clazz;
    }
}
