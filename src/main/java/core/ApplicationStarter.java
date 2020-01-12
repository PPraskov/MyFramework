package core;

import core.execution.ApplicationManager;

public final class ApplicationStarter {
    private static ApplicationStarter ourInstance = null;

    private ApplicationStarter() {
    }

    public synchronized static void startApplication(Class starterClass) {
        String applicationPackageName = starterClass.getPackage().getName();
        String corePackageName = ApplicationStarter.class.getPackage().getName();
        if (ourInstance == null) {
            ourInstance = new ApplicationStarter();
        }

        ApplicationManager.getManager().startApplication(applicationPackageName,corePackageName);
    }


    private void startHttpServer() {
//        HttpControllerInitializer initializer = new ControllerInitializer();
//        initializer.initialize();
//        HttpServerStarter starter = new HttpServerManager();
//        starter.start();
    }
}
