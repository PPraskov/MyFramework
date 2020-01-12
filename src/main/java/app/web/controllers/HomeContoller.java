package app.web.controllers;

import app.service.DemoService;
import core.annotations.dependency.Autowire;
import core.annotations.dependency.Controller;
import core.annotations.httpmapping.httprequesttype.HttpMethod;
import core.annotations.httpmapping.httprequesttype.MethodMapping;

@Controller(mapping = "/")
public class HomeContoller {

    private final DemoService demoService;

    @Autowire
    public HomeContoller(DemoService demoService) {
        this.demoService = demoService;
    }

    @MethodMapping(method = HttpMethod.GET)
    public void getIndex(){

    }
}
