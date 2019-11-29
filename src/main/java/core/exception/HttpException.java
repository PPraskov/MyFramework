package core.exception;

public class HttpException extends RuntimeException {

    private int httpCode;

    public HttpException(int code, String message){
        super(message);
        this.httpCode = code;
    }

    public int getHttpCode() {
        return httpCode;
    }
}
