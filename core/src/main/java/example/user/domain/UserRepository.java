package example.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPlatformAndPlatformId(Platform platform, String platformId);

    Optional<User> findUserByPlatformAndPlatformId(Platform platform, String platformId);
}
