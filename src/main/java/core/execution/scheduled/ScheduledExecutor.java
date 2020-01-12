package core.execution.scheduled;

class ScheduledExecutor extends Thread {

    private final ScheduledTaskContainer container;

    ScheduledExecutor(ScheduledTaskContainer container) {
        this.setName("Scheduled Executor");
        this.setPriority(8);
        this.container = container;
    }

    @Override
    public void run() {
        //TODO
    }
}
