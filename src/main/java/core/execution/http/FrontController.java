package core.execution.http;

import core.exception.RedirectionException;
import core.exception.ResourceException;

class FrontController {

    private ThreadLocal<HttpRequestThreadLocal> threadLocalRequest;

    void executeChain(ThreadLocal<HttpRequestThreadLocal> request) throws RedirectionException {
        this.threadLocalRequest = request;
        checkPathForResources();

    }

    private void checkPathForResources() throws RedirectionException {
        if (getRequestPath().contains(".")) {
            String afterDot = getRequestPath().substring(getRequestPath().lastIndexOf("."));
            if ((afterDot.equals(".js") || afterDot.equals(".css"))
                    && getRequestMethod().equalsIgnoreCase("get")) {
                throw new ResourceException(getRequestPath());
            }
        }
    }

    private String getRequestPath() {
        return threadLocalRequest.get().getExchange().getRequestURI().getPath();
    }

    private String getRequestMethod() {
        return threadLocalRequest.get().getExchange().getRequestMethod().toUpperCase();
    }
}
