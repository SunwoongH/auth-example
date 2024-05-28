package example.bridge.openfeign;

public interface KakaoOAuthProviderAdapter {
    String getPlatformId(String accessToken);
}
