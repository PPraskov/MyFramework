package core;

import core.dependencymanager.DependencyInitializeManager;
import core.dependencymanager.DependencyInitializer;
import core.execution.HttpServerManager;
import core.execution.HttpServerStarter;
import core.http.controller.ControllerInitializer;
import core.http.controller.HttpControllerInitializer;

import java.io.IOException;

public class ApplicationBooter {
    private static ApplicationBooter ourInstance = null;

    private ApplicationBooter() {
    }

    public synchronized static void startApplication() {

        if (ourInstance == null) {
            ourInstance = new ApplicationBooter();
        }
        ourInstance.initializeApplication();
    }

    private void initializeApplication() {
        DependencyInitializer dependencyInitializer = new DependencyInitializeManager();
        dependencyInitializer.initialize();
        boolean notGud;
        int fiveTimes = 0;
        do {
            notGud = false;
            try {
                startHttpServer();
            } catch (IOException e) {
                notGud = true;
                fiveTimes++;
                e.printStackTrace();
            }
        } while (notGud && fiveTimes < 5);
    }

    private void startHttpServer() throws IOException {
        HttpControllerInitializer initializer = new ControllerInitializer();
        initializer.initialize();
        HttpServerStarter starter = new HttpServerManager();
        starter.start();
    }
}
