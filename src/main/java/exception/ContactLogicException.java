package exception;

public class ContactLogicException extends Exception
{
    public ContactLogicException() {
    }

    public ContactLogicException(String message) {
        super(message);
    }

    public ContactLogicException(Throwable cause) {
        super(cause);
    }

    public ContactLogicException(String message, Throwable cause) {
        super(message, cause);
    }
}
