package core.execution.http.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

final class ControllerContainer {
    private static ControllerContainer instance = null;
    private AtomicReference<Map<String, ControllerData>> controllers;

    private ControllerContainer(Map<String, ControllerData> controllers) {
        this.controllers = new AtomicReference<>(controllers);
    }

    static void init(Map<String, ControllerData> controllers) {
        instance = new ControllerContainer(controllers);
    }

    static synchronized ControllerContainer getInstance() {
        return instance;
    }

    Map<String, ControllerData> getControllers() {
        return Collections.unmodifiableMap(new HashMap<>(this.controllers.get()));
    }
}
