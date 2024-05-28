package example.user.service.response;

import example.bridge.jwt.Token;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserAuthServiceResponse(
        String accessToken,
        String refreshToken
) {
    public static UserAuthServiceResponse of(Token token) {
        return UserAuthServiceResponse.builder()
                .accessToken(token.accessToken())
                .refreshToken(token.refreshToken())
                .build();
    }
}
