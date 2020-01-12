package core.execution.http.server;

import core.execution.LockCheck;

public final class HttpExecutor extends Thread {

    private TaskContainer container;
    private LockCheck lockCheck;
    private boolean toContinue;

    HttpExecutor(TaskContainer container) {
        this.container = container;
        this.setName("Http Executor");
        this.setDaemon(false);
        this.setPriority(8);
    }

    public void setLockCheck(LockCheck lockCheck){
        if (this.lockCheck == null){
            this.lockCheck = lockCheck;
        }
    }

    @Override
    @SuppressWarnings("")
    public void run() {
        while (true) {
            Runnable runnable = this.container.getTask();
            if (runnable == null){
                continue;
            }
            assignThread(runnable);
        }
    }

    private void assignThread(Runnable runnable){
        Thread thread = new Thread(runnable);
        thread.setDaemon(false);
        thread.setPriority(2);
        thread.start();
    }

}
