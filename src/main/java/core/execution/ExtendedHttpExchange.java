package core.execution;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExtendedHttpExchange {

    private final HttpExchange rawExchange;
    private final Map<String, String> queryParameters;
    private final Map<String, String> inputFormParameters;

    ExtendedHttpExchange(HttpExchange rawExchange) {
        this.rawExchange = rawExchange;
        queryParameters = setQueryParameters();
        inputFormParameters = setInputFormParameters();
    }

    String getPath(){
        return this.rawExchange.getRequestURI().getPath();
    }

    String getHttpMethod() {
        return this.rawExchange.getRequestMethod().toUpperCase();
    }
    String getParameterKeysAsString(){
        return String.join(",",getQueryParameterKeysAsList());
    }

    private String setHttpMethod() {
        return this.rawExchange.getRequestMethod().toUpperCase();
    }

    private String setController() {
        return this.rawExchange.getRequestURI().getPath();
    }

    public Map<String, String> getQueryParameters() {
        return this.queryParameters == null ? new HashMap<>() : this.queryParameters;
    }

    List<String> getQueryParameterKeysAsList() {
        return getQueryParameters().keySet().stream().sorted(String::compareToIgnoreCase).collect(Collectors.toList());
    }

    public Map<String, String> getInputFormParameters() {
        return this.inputFormParameters == null ? new HashMap<>() : this.inputFormParameters;
    }

    private Map<String, String> setQueryParameters() {
        if (this.rawExchange.getRequestURI().getQuery().isEmpty()) {
            return null;
        }
        return fromStringToMap(this.rawExchange.getRequestURI().getQuery());
    }

    private Map<String, String> fromStringToMap(String array) {
        String[] strings = array.split("&");
        Map<String, String> queryParameters = new HashMap<>();
        for (int i = 0; i < strings.length; i++) {
            String[] kvp = strings[i].split("=");
            String key = kvp[0];
            String value = kvp[1];
            queryParameters.put(key, value);
        }
        return queryParameters;
    }

    private Map<String, String> setInputFormParameters() {
        String input = readInputStream();
        if (input.isEmpty()) {
            return null;
        }
        return fromStringToMap(input);
    }

    private String readInputStream() {
        InputStream requestBody = this.rawExchange.getRequestBody();
        StringBuffer sb = new StringBuffer();
        int i;
        char c;
        try {
            while ((i = requestBody.read()) != -1) {
                c = (char) i;
                sb.append(c);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
