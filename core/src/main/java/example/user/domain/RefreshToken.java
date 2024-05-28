package example.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@RedisHash(value = "refreshToken", timeToLive = 604800000)
public class RefreshToken {
    @Id
    private Long id;
    private String refreshToken;
}
