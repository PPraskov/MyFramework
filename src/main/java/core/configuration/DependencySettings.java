package core.configuration;

import core.annotations.dependency.Bean;
import core.annotations.dependency.Component;
import core.annotations.dependency.Repository;
import core.annotations.dependency.Service;

import java.util.HashSet;
import java.util.Set;

public class DependencySettings {

    private boolean singletonPattern;
    private Set<Class> exclusions;

    public DependencySettings() {
        this.singletonPattern = false;
        this.exclusions = new HashSet<>();
    }

    public final DependencySettings enableSingletonPattern() {
        this.singletonPattern = true;
        return this;
    }

    public final DependencySettings excludeRepositories() {
        exclusions.add(Repository.class);
        return this;
    }

    public final DependencySettings excludeServicesAndComponents() {
        exclusions.add(Component.class);
        exclusions.add(Service.class);
        return this;
    }

    public final DependencySettings excludeBeans() {
        exclusions.add(Bean.class);
        return this;
    }


}
