package core.execution.http.server;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;

final class TaskContainer  implements Executor {

    private volatile BlockingDeque<Runnable> tasks;

    TaskContainer() {
        this.tasks = new LinkedBlockingDeque<>();
    }

    private void submitTask(Runnable r){
        this.tasks.add(r);
    }

    @Override
    public void execute(Runnable command) {
        submitTask(command);
    }

    Runnable getTask(){
        if (this.tasks.isEmpty()){
            return null;
        }
        return this.tasks.pop();
    }
}
