package example;

import example.bridge.jwt.JwtHandlerAdapter;
import example.bridge.jwt.Token;
import example.exception.UnauthorizedException;
import example.message.ErrorMessage;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtHandlerAdapterImpl implements JwtHandlerAdapter {
    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.access-token-expire-time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;
    @Value("${jwt.refresh-token-expire-time}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Override
    public Token issueToken(Long userId) {
        return Token.of(generateToken(userId, true),
                generateToken(userId, false));
    }

    @Override
    public Long getSubject(String token) {
        JwtParser jwtParser = getJwtParser();
        return Long.valueOf(jwtParser.parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    @Override
    public void validateAccessToken(String accessToken) {
        try {
            parseToken(accessToken);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorMessage.EXPIRED_ACCESS_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorMessage.INVALID_ACCESS_TOKEN_VALUE);
        }
    }

    @Override
    public void validateRefreshToken(String refreshToken) {
        try {
            parseToken(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException(ErrorMessage.EXPIRED_REFRESH_TOKEN);
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorMessage.INVALID_REFRESH_TOKEN_VALUE);
        }
    }

    @Override
    public void equalsRefreshToken(String refreshToken, String storedRefreshToken) {
        if (!refreshToken.equals(storedRefreshToken)) {
            throw new UnauthorizedException(ErrorMessage.MISMATCH_REFRESH_TOKEN);
        }
    }

    private String generateToken(Long userId, boolean isAccessToken) {
        final Date now = generateNowDate();
        final Date expiration = generateExpirationDate(isAccessToken, now);
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Date generateNowDate() {
        return new Date();
    }

    private Date generateExpirationDate(boolean isAccessToken, Date now) {
        return new Date(now.getTime() + calculateExpirationTime(isAccessToken));
    }

    private long calculateExpirationTime(boolean isAccessToken) {
        if (isAccessToken) {
            return ACCESS_TOKEN_EXPIRE_TIME;
        }
        return REFRESH_TOKEN_EXPIRE_TIME;
    }

    private void parseToken(String token) {
        JwtParser jwtParser = getJwtParser();
        jwtParser.parseClaimsJws(token);
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(encodeSecretKey().getBytes());
    }

    private String encodeSecretKey() {
        return Base64.getEncoder()
                .encodeToString(secretKey.getBytes());
    }
}
