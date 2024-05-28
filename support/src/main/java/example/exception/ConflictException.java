package example.exception;

import example.message.ErrorMessage;

public class ConflictException extends BusinessException {
    public ConflictException() {
        super(ErrorMessage.CONFLICT);
    }

    public ConflictException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
