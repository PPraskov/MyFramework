package core.exception;

public class ResourceException extends RuntimeException {

    private String resource;

    public ResourceException(String resource) {
        this.resource = resource;
    }

    public String getResourceString() {
        return resource;
    }
}
