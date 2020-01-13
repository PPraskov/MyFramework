package core.execution.http;

import org.thymeleaf.context.Context;

public class ContextAndView {

    private final Context context;
    private String viewName;
    private String redirectTo;
    private Integer responseCode;

    ContextAndView(Context context) {
        this.context = context;
    }

    public void setViewName(String viewName) {
        this.viewName = String.format("%s%s", "/", viewName);
        this.redirectTo = null;
    }

    public Context getContext() {
        return context;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
        this.responseCode = 307;
        this.viewName = null;
    }

    String getViewName() {
        return viewName;
    }
}
