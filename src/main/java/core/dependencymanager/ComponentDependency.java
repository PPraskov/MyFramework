package core.dependencymanager;

import java.lang.reflect.Constructor;

class ComponentDependency {

    private final DependencyType dependencyType;

    private final Constructor constructor;

    ComponentDependency(Constructor constructor, DependencyType dependencyType) {
        this.constructor = constructor;
        this.dependencyType = dependencyType;
    }


    Constructor getConstructor() {
        return constructor;
    }

    DependencyType getDependencyType() {
        return dependencyType;
    }
}
