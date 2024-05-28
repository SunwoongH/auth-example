package example.exception;

import example.message.ErrorMessage;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException() {
        super(ErrorMessage.UNAUTHORIZED);
    }

    public UnauthorizedException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
