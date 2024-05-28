package example.exception;

import example.message.ErrorMessage;

public class EntityNotFoundException extends BusinessException {
    public EntityNotFoundException() {
        super(ErrorMessage.ENTITY_NOT_FOUND);
    }

    public EntityNotFoundException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
