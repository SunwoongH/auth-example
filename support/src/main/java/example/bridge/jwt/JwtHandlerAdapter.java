package example.bridge.jwt;

public interface JwtHandlerAdapter {
    Token issueToken(Long userId);

    Long getSubject(String token);

    void validateAccessToken(String accessToken);

    void validateRefreshToken(String refreshToken);

    void equalsRefreshToken(String refreshToken, String storedRefreshToken);
}
