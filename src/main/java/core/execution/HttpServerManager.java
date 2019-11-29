package core.execution;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class HttpServerManager implements HttpServerStarter {

    private HttpServer server;

    public HttpServerManager() {
    }

    @Override
    public void start() throws IOException {
        setUpHttpServer();
    }

    private void setUpHttpServer() throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8000), 0);
        TaskContainer container = new TaskContainer();
        this.server.setExecutor(container);
        this.server.createContext("/",new ExchangeHandler());
        MainExecutor executor = new MainExecutor(container);
        executor.start();
        this.server.start();
    }
}
