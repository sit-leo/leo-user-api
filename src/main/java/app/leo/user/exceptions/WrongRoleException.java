package app.leo.user.exceptions;

public class WrongRoleException extends HttpException {
    public WrongRoleException() {
    }

    public WrongRoleException(String message) {
        super(message);
    }

    public WrongRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongRoleException(Throwable cause) {
        super(cause);
    }

    public WrongRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
