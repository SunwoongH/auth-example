package example.exception;

import example.message.ErrorMessage;

public class InvalidValueException extends BusinessException {
    public InvalidValueException() {
        super(ErrorMessage.BAD_REQUEST);
    }

    public InvalidValueException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
