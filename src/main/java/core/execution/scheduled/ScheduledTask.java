package core.execution.scheduled;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class ScheduledTask {

    private final Class aClass;
    private final Method method;
    private final Constructor constructor;

    ScheduledTask(Class aClass, Method method, Constructor constructor) {
        this.aClass = aClass;
        this.method = method;
        this.constructor = constructor;
    }

}
