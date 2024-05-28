package example.user.service.request;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserSignInServiceRequest(
        String platform,
        String accessToken
) {
    public static UserSignInServiceRequest of(String platform, String accessToken) {
        return UserSignInServiceRequest.builder()
                .platform(platform)
                .accessToken(accessToken)
                .build();
    }
}
