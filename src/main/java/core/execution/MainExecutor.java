package core.execution;

final class MainExecutor extends Thread {

    private TaskContainer container;

    MainExecutor(TaskContainer container) {
        this.container = container;
        this.setName("Main Executor");
        this.setDaemon(true);
        this.setPriority(8);
    }


    @Override
    @SuppressWarnings("")
    public void run() {
        while (true) {
            assignTasks();
        }
    }

    private void assignTasks() {
        Runnable runnable = this.container.getTask();
        if (runnable != null) {
            setUpThreadAndStart(runnable);
        }
    }

    private void setUpThreadAndStart(Runnable runnable){
        Thread thread = new Thread(runnable, "Exchange Handler");
        thread.setPriority(5);
        thread.setDaemon(false);
        thread.start();
    }
}
