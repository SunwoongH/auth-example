package example.user.api.request;

import example.user.service.request.UserSignInServiceRequest;

public record UserSignInRequest(
        String platform
) {
    public UserSignInServiceRequest toServiceDto(String accessToken) {
        return UserSignInServiceRequest.of(platform, accessToken);
    }
}
