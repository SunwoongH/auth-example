package example.user.service.request;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record UserReissueServiceRequest(
        Long id,
        String refreshToken
) {
    public static UserReissueServiceRequest of(Long id, String refreshToken) {
        return UserReissueServiceRequest.builder()
                .id(id)
                .refreshToken(refreshToken)
                .build();
    }
}
