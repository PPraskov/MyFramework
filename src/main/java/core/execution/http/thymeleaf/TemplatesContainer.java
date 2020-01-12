package core.execution.http.thymeleaf;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

class TemplatesContainer {
    private static TemplatesContainer instance = null;

    private final AtomicReference<Map<String,Template>> templates;

    static synchronized void init(Map<String, Template> templates){
        if (instance == null){
            instance = new TemplatesContainer(templates);
        }
    }

    static synchronized TemplatesContainer getInstance(){
        return instance;
    }

    private TemplatesContainer(Map<String, Template> templates) {
        this.templates = new AtomicReference<>(templates);
    }

    String getTemplates(String path) {
        return new String(templates.get().get(path).getContent());
    }
}
