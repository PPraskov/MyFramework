package core.execution.scheduled;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

class ScheduledTaskContainer {

    private final AtomicReference<Map<LocalDateTime, Class>> tasks;

    ScheduledTaskContainer(Map<LocalDateTime, Class> tasks) {
        this.tasks = new AtomicReference<>(tasks);
    }


}
