package app.utills;


import app.beans.DummyBean;
import core.annotations.dependency.Component;
import core.annotations.dependency.Autowire;

@Component
public class DemoUtill {

    private final DummyBean dummyBean;

    @Autowire
    public DemoUtill(DummyBean dummyBean) {
        this.dummyBean = dummyBean;
    }
}
