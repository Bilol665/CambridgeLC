package uz.pdp.cambridgelc.exceptions;

public class FailedAuthorizeException extends RuntimeException{
    public FailedAuthorizeException(String message) {
        super(message);
    }
}
