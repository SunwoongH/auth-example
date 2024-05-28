package example.kakao;

import example.kakao.response.KakaoFeignInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "kakao-feign-client", url = "https://kapi.kakao.com/v1/user/access_token_info")
public interface KakaoFeignClient {
    @GetMapping
    KakaoFeignInfoResponse getKakaoTokenInfo(@RequestHeader("Authorization") String accessToken);
}
