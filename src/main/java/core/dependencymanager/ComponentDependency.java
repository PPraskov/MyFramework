package core.dependencymanager;

import java.lang.reflect.Constructor;

class ComponentDependency {

    private final Constructor constructor;

    ComponentDependency(Constructor constructor) {
        this.constructor = constructor;
    }


    Constructor getConstructor() {
        return constructor;
    }
}
