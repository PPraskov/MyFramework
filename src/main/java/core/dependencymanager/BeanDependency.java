package core.dependencymanager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

class BeanDependency {

    private final DependencyType dependencyType;
    private final Constructor emptyConstructor;
    private final Method buildMethod;

    BeanDependency(DependencyType dependencyType, Constructor emptyConstructor, Method buildMethod) {
        this.dependencyType = dependencyType;
        this.emptyConstructor = emptyConstructor;
        this.buildMethod = buildMethod;
    }

    Constructor getEmptyConstructor() {
        return emptyConstructor;
    }

    Method getBuildMethod() {
        return buildMethod;
    }

    DependencyType getDependencyType() {
        return dependencyType;
    }

}
