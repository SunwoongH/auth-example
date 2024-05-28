package example.user.domain;

import example.exception.InvalidValueException;
import example.message.ErrorMessage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Platform {
    KAKAO("kakao");

    private final String name;

    public static Platform from(String platform) {
        return Arrays.stream(Platform.values())
                .filter(p -> p.getName().equals(platform))
                .findAny()
                .orElseThrow(() -> new InvalidValueException(ErrorMessage.INVALID_PLATFORM_TYPE));
    }
}
