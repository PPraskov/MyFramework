package app.web.controllers;

import app.service.DemoService;
import core.annotations.dependency.Autowire;
import core.annotations.dependency.Component;
import core.annotations.dependency.Controller;
import core.annotations.httpmapping.httprequesttype.HttpMethod;
import core.annotations.httpmapping.httprequesttype.MethodMapping;
import core.execution.http.ContextAndView;
import core.execution.http.utill.HttpRequestHeaders;
import core.execution.http.utill.HttpResponseHeaders;

@Controller(mapping = "/")
public class HomeController {

    private final DemoService demoService;

    @Autowire
    public HomeController(DemoService demoService) {
        this.demoService = demoService;
    }

    @MethodMapping(method = HttpMethod.GET)
    public void getIndex(HttpRequestHeaders requestHeaders, HttpResponseHeaders responseHeaders, ContextAndView contextAndView){
        contextAndView.getContext().setVariable("var","Hello from the HomeController!");
        contextAndView.getContext().setVariable("hidden","Am i really hidden?");
        contextAndView.setViewName("index");
    }
}
