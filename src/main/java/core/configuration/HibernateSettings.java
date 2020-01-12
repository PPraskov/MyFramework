package core.configuration;

import java.util.Map;
import java.util.Set;

public final class HibernateSettings {

    private Map<String, String> settings;
    private Set<Class> models;

    public HibernateSettings(Map<String, String> settings, Set<Class> models) {
        this.settings = settings;
        this.models = models;
    }

    public Map<String, String> getSettings() {
        return this.settings;
    }

    public Set<Class> getModels() {
        return this.models;
    }
}
