package example.user.service;

import example.bridge.jwt.JwtHandlerAdapter;
import example.bridge.jwt.Token;
import example.bridge.openfeign.KakaoOAuthProviderAdapter;
import example.exception.ConflictException;
import example.exception.EntityNotFoundException;
import example.exception.UnauthorizedException;
import example.message.ErrorMessage;
import example.user.domain.*;
import example.user.service.request.UserReissueServiceRequest;
import example.user.service.request.UserSignInServiceRequest;
import example.user.service.request.UserSignUpServiceRequest;
import example.user.service.response.UserAuthServiceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtHandlerAdapter jwtHandlerAdapter;
    private final KakaoOAuthProviderAdapter kakaoOAuthProviderAdapter;

    public UserAuthServiceResponse signUp(UserSignUpServiceRequest request) {
        Platform platform = Platform.from(request.platform());
        String platformId = kakaoOAuthProviderAdapter.getPlatformId(request.accessToken());
        validateDuplicateUser(platform, platformId);
        User user = User.builder()
                .name(request.name())
                .platform(platform)
                .platformId(platformId)
                .build();
        User savedUser = userRepository.save(user);
        Token issuedToken = issueTokenAndStoreRefreshToken(savedUser.getId());
        return UserAuthServiceResponse.of(issuedToken);
    }

    public UserAuthServiceResponse signIn(UserSignInServiceRequest request) {
        Platform platform = Platform.from(request.platform());
        String platformId = kakaoOAuthProviderAdapter.getPlatformId(request.accessToken());
        User findUser = findUserBy(platform, platformId);
        Token issuedToken = issueTokenAndStoreRefreshToken(findUser.getId());
        return UserAuthServiceResponse.of(issuedToken);
    }

    @Transactional(noRollbackFor = UnauthorizedException.class)
    public UserAuthServiceResponse reissue(UserReissueServiceRequest request) {
        try {
            jwtHandlerAdapter.validateRefreshToken(request.refreshToken());
            Long userId = jwtHandlerAdapter.getSubject(request.refreshToken());
            RefreshToken findRefreshToken = findRefreshTokenBy(userId);
            jwtHandlerAdapter.equalsRefreshToken(request.refreshToken(), findRefreshToken.getRefreshToken());
            Token issuedToken = issueTokenAndStoreRefreshToken(userId);
            return UserAuthServiceResponse.of(issuedToken);
        } catch (UnauthorizedException e) {
            signOut(request.id());
            throw e;
        }
    }

    public void signOut(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }

    public void withdrawal(Long userId) {
        userRepository.deleteById(userId);
    }

    private void validateDuplicateUser(Platform platform, String platformId) {
        boolean isExistingUser = userRepository.existsByPlatformAndPlatformId(platform, platformId);
        if (isExistingUser) {
            throw new ConflictException(ErrorMessage.DUPLICATE_USER);
        }
    }

    private User findUserBy(Platform platform, String platformId) {
        return userRepository.findUserByPlatformAndPlatformId(platform, platformId)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.USER_NOT_FOUND));
    }

    private RefreshToken findRefreshTokenBy(Long userId) {
        return refreshTokenRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException(ErrorMessage.EXPIRED_REFRESH_TOKEN));
    }

    private Token issueTokenAndStoreRefreshToken(Long userId) {
        Token issuedToken = jwtHandlerAdapter.issueToken(userId);
        RefreshToken refreshToken = RefreshToken.builder()
                .id(userId)
                .refreshToken(issuedToken.refreshToken())
                .build();
        refreshTokenRepository.save(refreshToken);
        return issuedToken;
    }
}
