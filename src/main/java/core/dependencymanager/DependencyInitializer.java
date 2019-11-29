package core.dependencymanager;

import core.exception.BeanException;
import core.exception.DependencyException;

public interface DependencyInitializer {

    void initialize() throws BeanException, DependencyException;
}
