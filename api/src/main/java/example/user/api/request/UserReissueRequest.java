package example.user.api.request;

import example.user.service.request.UserReissueServiceRequest;

public record UserReissueRequest(
        Long id
) {
    public UserReissueServiceRequest toServiceDto(String refreshToken) {
        return UserReissueServiceRequest.of(id, refreshToken);
    }
}
