package example.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import example.message.ErrorMessage;
import example.message.SuccessMessage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BaseResponse<T> {
    private final int status;
    private final String code;
    private final String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private final T data;

    public static BaseResponse<?> of(SuccessMessage successMessage) {
        return builder()
                .status(successMessage.getStatus())
                .code(successMessage.getCode())
                .message(successMessage.getMessage())
                .build();
    }

    public static <T> BaseResponse<?> of(SuccessMessage successMessage, T data) {
        return builder()
                .status(successMessage.getStatus())
                .code(successMessage.getCode())
                .message(successMessage.getMessage())
                .data(data)
                .build();
    }

    public static BaseResponse<?> of(ErrorMessage errorMessage) {
        return builder()
                .status(errorMessage.getStatus())
                .code(errorMessage.getCode())
                .message(errorMessage.getMessage())
                .build();
    }
}