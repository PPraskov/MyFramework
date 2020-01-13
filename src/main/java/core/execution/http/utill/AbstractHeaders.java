package core.execution.http.utill;

import com.sun.net.httpserver.Headers;

abstract class AbstractHeaders {

    private final Headers headers;

    AbstractHeaders(Headers headers) {
        this.headers = headers;
    }

    public Headers getHeaders() {
        return headers;
    }
}
