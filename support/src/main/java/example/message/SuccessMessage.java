package example.message;

import example.common.HttpStatusConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum SuccessMessage {
    /**
     * 200 Ok
     */
    OK(HttpStatusConstants.OK, "s2000", "요청이 성공했습니다."),

    /**
     * 201 Created
     */
    CREATED(HttpStatusConstants.CREATED, "s2010", "요청이 성공했습니다.");

    private final int status;
    private final String code;
    private final String message;
}
