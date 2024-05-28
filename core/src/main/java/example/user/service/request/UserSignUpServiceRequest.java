package example.user.service.request;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserSignUpServiceRequest(
        String name,
        String platform,
        String accessToken
) {
    public static UserSignUpServiceRequest of(String name, String platform, String accessToken) {
        return UserSignUpServiceRequest.builder()
                .name(name)
                .platform(platform)
                .accessToken(accessToken)
                .build();
    }
}
