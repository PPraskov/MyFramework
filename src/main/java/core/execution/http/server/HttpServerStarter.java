package core.execution.http.server;

import java.io.IOException;

public interface HttpServerStarter {

    HttpExecutor start() throws IOException;
}
