package core.execution;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class Lock implements LockCheck,Locker {

    private ConcurrentMap<Class, Boolean> classes;


    Lock() {
        this.classes = new ConcurrentHashMap<>();
    }

    @Override
    public void addMe(Class aClass) {
        this.classes.put(aClass, false);
    }

    @Override
    public void lockObject(Class aClass) {
        this.classes.replace(aClass, false, true);

    }

    @Override
    public void unlockObject(Class aClass) {
        this.classes.replace(aClass, true, false);
    }

    @Override
    public void checkLock(Class aClass) {
        if (this.classes.containsKey(aClass)) {
            lock(aClass);
        }
    }

    private synchronized void lock(Class aClass) {
        if (this.classes.containsKey(aClass)) {
            do {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (this.classes.get(aClass));
        }
    }
}
