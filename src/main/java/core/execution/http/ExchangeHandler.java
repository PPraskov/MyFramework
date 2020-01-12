package core.execution.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import core.dependencymanager.DependencyBuilder;
import core.dependencymanager.DependencyResolveManager;
import core.exception.HttpException;
import core.exception.RedirectionException;
import core.exception.ResourceException;
import core.execution.http.controller.ControllerManager;
import core.execution.http.controller.HttpControllerFinder;
import core.execution.http.thymeleaf.ThymeleafResourceHandler;

import java.io.IOException;
import java.lang.reflect.Method;

public class ExchangeHandler implements HttpHandler {

    private ThreadLocal<HttpRequestThreadLocal> request;

    public ExchangeHandler() {
    }

    @Override
    public void handle(HttpExchange exchange) {
        try {
            this.request = new ThreadLocal<>();
            this.request.set(new HttpRequestThreadLocal(exchange));
            FrontController frontController = new FrontController();
            frontController.executeChain(this.request);
            System.out.println(this.request.get().getExchange().getRequestURI().toString());
            HttpControllerFinder controllerFinder = new ControllerManager();
            controllerFinder.findController(
                    this.request.get().getExchange().getRequestURI().getPath(),
                    this.request.get().getExchange().getRequestMethod().toUpperCase(),
                    "");
            Class controller = controllerFinder.getControllerClazz();
            Method controllerMethod = controllerFinder.getControllerMethod();
            DependencyBuilder builder = new DependencyResolveManager();
            Object controllerObject = builder.buildComponent(controller);
            controllerMethod.invoke(controllerObject,
                    builder.buildControllerMethodDependencies(controllerMethod,this.request.get()));
            String html = new ThymeleafResourceHandler().findResource(this.request.get().getContextAndView().getViewName(),
                    this.request.get().getContext());

            sendResponse(this.request.get().getExchange(),200,html);

        } catch (ResourceException re) {
            exchange.getResponseHeaders().putAll(this.request.get().getExchange().getRequestHeaders());
            String resource = new ThymeleafResourceHandler().findResource(re.getResourceString(), this.request.get().getContext());
            sendResponse(this.request.get().getExchange(), 200, resource);
        } catch (RedirectionException re) {
            sendResponse(this.request.get().getExchange(), 307, re.getRedirectString());
        } catch (HttpException httpException) {
            System.out.println();
        } catch (Throwable throwable) {
            sendResponse(this.request.get().getExchange(), 500, null);
        } finally {
            this.request.get().getExchange().close();
        }
    }

    private void sendResponse(HttpExchange exchange, int code, String resource) {
        try {
            setResponseHeaders(exchange);
            long length = 0;
            if (resource != null) {
                length = resource.length();
            }
            if (code >= 300 && code < 400) {
                length = 0;
                exchange.getResponseHeaders().add("Location", resource);
            }
            exchange.sendResponseHeaders(code, length);
            if (length > 0) {
                exchange.getResponseBody().write(resource.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setResponseHeaders(HttpExchange exchange) {
        exchange.getRequestHeaders().entrySet().forEach(e -> exchange.getResponseHeaders().putIfAbsent(e.getKey(), e.getValue()));
    }
}