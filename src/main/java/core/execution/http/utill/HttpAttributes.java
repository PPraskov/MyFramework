package core.execution.http.utill;

import java.util.Map;

public class HttpAttributes {

    private final Map<String,Object> attributes;

    public HttpAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }
}
