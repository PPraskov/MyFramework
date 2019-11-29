package app.services;


import app.utills.DemoUtill;
import core.annotations.dependency.Autowire;


public class DemoServiceImpl implements DemoService{

    private final DemoUtill demoUtill;

    @Autowire
    public DemoServiceImpl(DemoUtill demoUtill) {
        this.demoUtill = demoUtill;
    }


}
