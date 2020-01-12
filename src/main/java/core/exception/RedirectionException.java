package core.exception;

public class RedirectionException extends RuntimeException {
    private String redirectTo;

    public RedirectionException(String redirect) {
        this.redirectTo = redirect;
    }

    public String getRedirectString() {
        return redirectTo;
    }

}
