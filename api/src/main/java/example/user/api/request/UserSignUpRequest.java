package example.user.api.request;

import example.user.service.request.UserSignUpServiceRequest;

public record UserSignUpRequest(
        String name,
        String platform
) {
    public UserSignUpServiceRequest toServiceDto(String accessToken) {
        return UserSignUpServiceRequest.of(name, platform, accessToken);
    }
}
