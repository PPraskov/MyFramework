package core.execution;

import core.dependencymanager.DependencyBuilder;
import core.dependencymanager.DependencyInitializeManager;
import core.dependencymanager.DependencyInitializer;
import core.dependencymanager.DependencyResolveManager;
import core.execution.http.controller.ControllerInitializer;
import core.execution.http.controller.HttpControllerInitializer;
import core.execution.http.server.HttpExecutor;
import core.execution.http.server.HttpServerManager;
import core.execution.http.server.HttpServerStarter;
import core.execution.http.thymeleaf.TemplateInitializer;
import core.execution.http.thymeleaf.TemplateManager;
import core.repository.RepositoryInitializer;
import core.repository.RepositoryInitializerImpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ApplicationManager {
    private static ApplicationManager instance = null;
    private AtomicBoolean appIsRunning = new AtomicBoolean(false);

    private ApplicationManager() {
    }

    public static synchronized ApplicationManager getManager() {
        if (instance == null) {
            instance = new ApplicationManager();
        }
        return instance;
    }

    public void startApplication(String applicationPackageName, String corePackageName) {
        if (appIsRunning.get()){
            return;
        }
        appIsRunning.set(true);

        try {
            initializeAndStartHibernate(applicationPackageName);
            initializeDependencies(applicationPackageName, corePackageName);
            initializeHttpControllers(applicationPackageName);
            initializeTemplates();
            startHttpServer();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void testDependencies() {
//        DependencyBuilder builder = new DependencyResolveManager();
//        try {
////            Object o = builder.buildController(DemoController.class);
//            System.out.println(o);
//        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }
    }

    private void initializeTemplates() throws IOException {
        TemplateInitializer initializer = new TemplateManager();
        initializer.initialize();
    }

    private void startHttpServer() throws IOException {
        HttpServerStarter starter = new HttpServerManager();
        HttpExecutor start = starter.start();
    }

    private void initializeHttpControllers(String applicationPackageName) {
        HttpControllerInitializer initializer = new ControllerInitializer();
        initializer.initialize(applicationPackageName);
    }

    private void initializeDependencies(String applicationPackageName, String corePackageName)
            throws NoSuchMethodException, InstantiationException,
            IllegalAccessException, InvocationTargetException {

        DependencyInitializer dependencyInitializer = new DependencyInitializeManager();
        dependencyInitializer.initialize(applicationPackageName, corePackageName);
    }

    private void initializeAndStartHibernate(String applicationPackageName)
            throws InvocationTargetException, NoSuchMethodException,
            InstantiationException, IllegalAccessException {

        RepositoryInitializer repositoryInitializer = new RepositoryInitializerImpl();
        repositoryInitializer.initialize(applicationPackageName);
    }
}
