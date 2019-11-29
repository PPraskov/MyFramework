package app;

import core.ApplicationBooter;

public class Starter {
    public static void main(String[] args) {
        System.out.println((Runtime.getRuntime().freeMemory()/1024)/1024);
        ApplicationBooter.startApplication();
    }
}
