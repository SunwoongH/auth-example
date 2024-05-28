package example;

import example.bridge.openfeign.KakaoOAuthProviderAdapter;
import example.exception.UnauthorizedException;
import example.kakao.KakaoFeignClient;
import example.kakao.response.KakaoFeignInfoResponse;
import example.message.ErrorMessage;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KakaoOAuthProviderAdapterImpl implements KakaoOAuthProviderAdapter {
    private final KakaoFeignClient kakaoFeignClient;

    @Override
    public String getPlatformId(String accessToken) {
        try {
            KakaoFeignInfoResponse kakaoTokenInfo = kakaoFeignClient.getKakaoTokenInfo("Bearer " + accessToken);
            return String.valueOf(kakaoTokenInfo.id());
        } catch (FeignException e) {
            throw new UnauthorizedException(ErrorMessage.INVALID_KAKAO_ACCESS_TOKEN);
        }
    }
}
