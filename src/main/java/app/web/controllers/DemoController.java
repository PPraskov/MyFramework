package app.web.controllers;


import app.beans.DummyBean;
import app.services.DemoServiceImpl;
import app.utills.DemoUtill;
import core.annotations.dependency.Autowire;
import core.annotations.dependency.Component;
import core.annotations.httpmapping.Controller;
import core.annotations.httpmapping.httprequesttype.HttpMethod;
import core.annotations.httpmapping.httprequesttype.MethodMapping;
import core.annotations.httpmapping.htttpparams.QueryParameters;

import java.util.Map;

@Controller(mapping = "/home")
@Component
public class DemoController {

    private final DemoUtill demoUtill;
    private final DemoServiceImpl demoService;
    private final DummyBean dummyBean;

    @Autowire
    public DemoController(DemoUtill demoUtill, DemoServiceImpl demoService, DummyBean dummyBean) {
        this.demoUtill = demoUtill;
        this.demoService = demoService;
        this.dummyBean = dummyBean;
    }

    @MethodMapping(method = HttpMethod.GET, params = "id,mazalo,patron")
    public void getHome(@QueryParameters core.utill.QueryParameters queryParams) {
        for (Map.Entry<String, String> entry : queryParams.getQueryParameters().entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }


}
