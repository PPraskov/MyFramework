package core.execution.http.utill;

import java.util.Map;

abstract class AbstractParameterMap {

    private final Map<String, String> params;

    AbstractParameterMap(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
