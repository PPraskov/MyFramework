package core.dependencymanager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class BeanDependency {

    private final Constructor emptyConstructor;
    private final Method buildMethod;

    BeanDependency(Class builderClass, Constructor emptyConstructor, Method buildMethod) {
        this.emptyConstructor = emptyConstructor;
        this.buildMethod = buildMethod;
    }

    Constructor getEmptyConstructor() {
        return emptyConstructor;
    }

    Method getBuildMethod() {
        return buildMethod;
    }
}
