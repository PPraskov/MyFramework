package core.execution.http;

import com.sun.net.httpserver.HttpExchange;
import core.execution.http.utill.*;
import org.thymeleaf.context.Context;

import java.util.Map;

public class HttpRequestThreadLocal {

    private HttpExchange exchange;
    private ContextAndView contextAndView;

    private Map<String, String> queryParams;
    private Map<String, String> formParams;

    public HttpRequestThreadLocal(HttpExchange exchange) {
        this.exchange = exchange;
        this.contextAndView = new ContextAndView(new Context());
    }

    public HttpExchange getExchange() {
        return exchange;
    }

    public Context getContext() {
        return this.contextAndView.getContext();
    }

    public ContextAndView getContextAndView() {
        return contextAndView;
    }

    public HttpAttributes getHttpAttributes(){
        return new HttpAttributes(this.exchange.getHttpContext().getAttributes());
    }

    public QueryParameters getQueryParameters(){
        return new QueryParameters(this.queryParams);
    }

    public FormParameters getFormParameters(){
        return new FormParameters(this.formParams);
    }

    public HttpRequestHeaders getHttpRequestHeaders(){
        return new HttpRequestHeaders(this.exchange.getRequestHeaders());
    }

    public HttpResponseHeaders getHttpResponseHeaders(){
        return new HttpResponseHeaders(this.exchange.getResponseHeaders());
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
