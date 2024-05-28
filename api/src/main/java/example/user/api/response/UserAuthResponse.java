package example.user.api.response;

import example.user.service.response.UserAuthServiceResponse;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserAuthResponse(
        String accessToken,
        String refreshToken
) {
    public static UserAuthResponse from(UserAuthServiceResponse response) {
        return UserAuthResponse.builder()
                .accessToken(response.accessToken())
                .refreshToken(response.refreshToken())
                .build();
    }
}
