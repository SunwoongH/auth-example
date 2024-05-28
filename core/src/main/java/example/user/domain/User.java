package example.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Platform platform;
    private String platformId;

    @Builder
    private User(String name, Platform platform, String platformId) {
        this.name = name;
        this.platform = platform;
        this.platformId = platformId;
    }
}
