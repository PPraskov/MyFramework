package core.execution.http;

import com.sun.net.httpserver.HttpExchange;
import org.thymeleaf.context.Context;

import java.util.Map;

public class HttpRequestThreadLocal {

    private HttpExchange exchange;
    private Context context;

    private Map<String, String> queryParams;
    private Map<String, String> formParams;

    public HttpRequestThreadLocal(HttpExchange exchange) {
        this.exchange = exchange;
        this.context = new Context();
    }

    public HttpExchange getExchange() {
        return exchange;
    }

    public Context getContext() {
        return context;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getFormParams() {
        return formParams;
    }

    public void setFormParams(Map<String, String> formParams) {
        this.formParams = formParams;
    }
}
