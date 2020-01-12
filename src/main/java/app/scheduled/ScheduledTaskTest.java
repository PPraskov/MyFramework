package app.scheduled;


import app.service.DemoService;
import core.annotations.dependency.Autowire;
import core.annotations.scheduled.Frequency;
import core.annotations.scheduled.ScheduledMethod;
import core.annotations.scheduled.ScheduledTask;

@ScheduledTask(ocurrence = Frequency.HOURLY, minute = "30")
public class ScheduledTaskTest {


    private final DemoService demoService;


    @Autowire
    public ScheduledTaskTest(DemoService demoService) {
        this.demoService = demoService;
    }

    @ScheduledMethod
    public void scheduledMethodToInvoke(){

    }
}
