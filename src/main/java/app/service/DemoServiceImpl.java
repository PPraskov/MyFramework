package app.service;


import app.repository.TestRepo;
import app.utill.DemoUtill;
import core.annotations.dependency.Autowire;


public class DemoServiceImpl implements DemoService{

    private final DemoUtill demoUtill;
    private final TestRepo testRepo;

    @Autowire
    public DemoServiceImpl(DemoUtill demoUtill, TestRepo testRepo) {
        this.demoUtill = demoUtill;
        this.testRepo = testRepo;
    }


}
