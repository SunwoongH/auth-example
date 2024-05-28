package example.message;

import example.common.HttpStatusConstants;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum ErrorMessage {
    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatusConstants.BAD_REQUEST, "e4000", "잘못된 요청입니다."),
    INVALID_PLATFORM_TYPE(HttpStatusConstants.BAD_REQUEST, "e4001", "유효하지 않은 플랫폼 타입입니다."),

    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatusConstants.UNAUTHORIZED, "e4010", "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatusConstants.UNAUTHORIZED, "e4011", "액세스 토큰의 형식이 올바르지 않습니다. Bearer 타입을 확인해 주세요."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatusConstants.UNAUTHORIZED, "e4012", "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatusConstants.UNAUTHORIZED, "e4013", "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatusConstants.UNAUTHORIZED, "e4014", "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatusConstants.UNAUTHORIZED, "e4015", "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
    MISMATCH_REFRESH_TOKEN(HttpStatusConstants.UNAUTHORIZED, "e4016", "리프레시 토큰이 일치하지 않습니다."),
    INVALID_KAKAO_ACCESS_TOKEN(HttpStatusConstants.UNAUTHORIZED, "e4017", "카카오 액세스 토큰의 정보를 조회하는 과정에서 오류가 발생하였습니다."),

    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatusConstants.FORBIDDEN, "e4030", "리소스 접근 권한이 없습니다."),

    /**
     * 404 Not Found
     */
    ENTITY_NOT_FOUND(HttpStatusConstants.NOT_FOUND, "e4040", "대상을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatusConstants.NOT_FOUND, "e4041", "존재하지 않는 회원입니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatusConstants.METHOD_NOT_ALLOWED, "e4050", "잘못된 HTTP method 요청입니다."),

    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatusConstants.CONFLICT, "e4090", "이미 존재하는 리소스입니다."),
    DUPLICATE_USER(HttpStatusConstants.CONFLICT, "e4091", "이미 존재하는 회원입니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatusConstants.INTERNAL_SERVER_ERROR, "e5000", "서버 내부 오류입니다.");

    private final int status;
    private final String code;
    private final String message;
}
