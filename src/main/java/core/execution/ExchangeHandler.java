package core.execution;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import core.dependencymanager.DependencyBuilder;
import core.dependencymanager.DependencyResolveManager;
import core.http.controller.ControllerManager;
import core.http.controller.HttpControllerFinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ExchangeHandler implements HttpHandler {

    private HttpExchange exchange;

    ExchangeHandler() {
    }

    @Override
    public void handle(HttpExchange exchange) {
        this.exchange = exchange;
        execute();
    }

    private void execute() {
        long start = System.currentTimeMillis();
        ExtendedHttpExchange exchange = new ExtendedHttpExchange(this.exchange);
        HttpControllerFinder controllerFinder = new ControllerManager();
        controllerFinder.findController(exchange.getPath(),exchange.getHttpMethod(),exchange.getParameterKeysAsString());
        Method m = controllerFinder.getControllerMethod();
        Class c = controllerFinder.getControllerClazz();
        try {
            DependencyBuilder dependencyBuilder = new DependencyResolveManager();
            dependencyBuilder.init(exchange);
            Object o = dependencyBuilder.buildController(c);
            Object[] objects = dependencyBuilder.buildMethodDependencies(m);
            m.invoke(o,objects);
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            this.exchange.close();
            System.out.println((System.currentTimeMillis()-start) + " ms.");
            System.out.println((Runtime.getRuntime().freeMemory()/1024)/1024);
        }
    }
}



