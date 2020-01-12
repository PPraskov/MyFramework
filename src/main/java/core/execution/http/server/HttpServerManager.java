package core.execution.http.server;

import com.sun.net.httpserver.HttpServer;
import core.execution.http.ExchangeHandler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public final class HttpServerManager implements HttpServerStarter {

    private HttpServer server;

    public HttpServerManager() {
    }

    @Override
    public HttpExecutor start() throws IOException {
        return setUpHttpServer();
    }

    private HttpExecutor setUpHttpServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress("localhost",8000), 0);
        TaskContainer container = new TaskContainer();
        this.server.setExecutor(container);
        this.server.createContext("/", new ExchangeHandler());
        HttpExecutor executor = new HttpExecutor(container);
        executor.start();
        this.server.start();
        return executor;
    }
}
