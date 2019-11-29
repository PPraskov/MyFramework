package core.utill;

import java.util.Map;

public class FormParameters {

    private Map<String,String> formParameters;

    public FormParameters(Map<String, String> formParameters) {
        this.formParameters = formParameters;
    }

    public Map<String, String> getFormParameters() {
        return formParameters;
    }
}
