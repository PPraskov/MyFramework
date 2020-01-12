package core.execution;

public interface Locker {

    void lockObject(Class aClass);
    void unlockObject(Class aClass);
    void addMe(Class aClass);
}
