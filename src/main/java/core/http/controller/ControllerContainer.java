package core.http.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

final class ControllerContainer {
    private static ControllerContainer instance = null;
    private AtomicReference<Map<String, ControllerData>> controllers;

    private ControllerContainer(Map<String, ControllerData> controllers) {
        this.controllers = new AtomicReference<>();
        this.controllers.set(controllers);
    }

    static void init(Map<String, ControllerData> controllers) {
        instance = new ControllerContainer(controllers);
    }

    static synchronized ControllerContainer getInstance() {
        return instance;
    }

    Map<String, ControllerData> getControllers() {
        return new HashMap<>(this.controllers.get());
    }
}
