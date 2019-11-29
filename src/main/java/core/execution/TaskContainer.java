package core.execution;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.ReentrantReadWriteLock;

final class TaskContainer  implements Executor {

    private volatile BlockingDeque<Runnable> tasks;
    private ReentrantReadWriteLock lock;

    TaskContainer() {
        this.tasks = new LinkedBlockingDeque<>();
        this.lock = new ReentrantReadWriteLock();
    }

    private void submitTask(Runnable runnable) {
        try {
            this.lock.writeLock().lock();
            if (this.lock.isWriteLockedByCurrentThread()) {
                this.tasks.add(runnable);
            }
        } finally {
            if (this.lock.isWriteLockedByCurrentThread()) {
                this.lock.writeLock().unlock();
            }
        }
    }


    Runnable getTask() {
        try{
            this.lock.writeLock().lock();
            if (this.lock.isWriteLockedByCurrentThread() && !this.tasks.isEmpty()) {
                return this.tasks.poll();
            }
        }finally {
            this.lock.writeLock().unlock();
        }
        return null;
    }

    @Override
    public void execute(Runnable command) {
        submitTask(command);
    }
}
