package example.user.facade;

import example.common.Constants;
import example.exception.BusinessException;
import example.message.ErrorMessage;
import example.user.domain.LettuceLockRepository;
import example.user.service.AuthService;
import example.user.service.request.UserReissueServiceRequest;
import example.user.service.request.UserSignInServiceRequest;
import example.user.service.request.UserSignUpServiceRequest;
import example.user.service.response.UserAuthServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthFacade {
    private final AuthService authService;
    private final LettuceLockRepository lettuceLockRepository;

    public UserAuthServiceResponse signUp(UserSignUpServiceRequest request) {
        while (!lettuceLockRepository.lock(request.accessToken(), Constants.SIGN_UP_LOCK)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new BusinessException(ErrorMessage.INTERNAL_SERVER_ERROR);
            }
        }
        try {
            return authService.signUp(request);
        } finally {
            lettuceLockRepository.unlock(request.accessToken());
        }
    }

    public UserAuthServiceResponse signIn(UserSignInServiceRequest request) {
        return authService.signIn(request);
    }

    public UserAuthServiceResponse reissue(UserReissueServiceRequest request) {
        return authService.reissue(request);
    }

    public void signOut(Long userId) {
        authService.signOut(userId);
    }

    public void withdrawal(Long userId) {
        authService.withdrawal(userId);
    }
}
