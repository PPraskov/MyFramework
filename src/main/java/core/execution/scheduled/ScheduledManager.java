package core.execution.scheduled;

import java.time.LocalDateTime;
import java.util.Map;

public class ScheduledManager {
    private static ScheduledManager instance = null;



    private ScheduledManager() {
    }

    public synchronized ScheduledManager manager(){
        return instance;
    }

    public synchronized void initializeAndStart(Map<LocalDateTime, Class> tasks){
        if (instance == null){
            instance = new ScheduledManager();
            ScheduledTaskContainer container = new ScheduledTaskContainer(tasks);
            ScheduledExecutor executor = new ScheduledExecutor(container);
            executor.start();
        }
    }


}
