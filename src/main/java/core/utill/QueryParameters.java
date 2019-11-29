package core.utill;

import java.util.Map;

public class QueryParameters {
    private Map<String,String> queryParameters;

    public QueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }
}
