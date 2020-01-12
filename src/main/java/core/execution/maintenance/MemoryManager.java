package core.execution.maintenance;

import core.execution.Locker;

public final class MemoryManager extends Thread{

    private boolean toContinue = true;

    private Locker locker;
    private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();
    private long freeMemory;

    public MemoryManager(Locker locker) {
        this.setName("Memory Manager");
        this.locker = locker;
        this.setPriority(10);
    }

    @Override
    public void run() {
        monitor();
    }

    public void askToStop(){
        toContinue = false;
    }

    private void monitor(){

        while (toContinue){
            //TODO
        }
    }

}
