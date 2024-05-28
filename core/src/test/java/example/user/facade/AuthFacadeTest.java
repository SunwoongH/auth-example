package example.user.facade;

import example.exception.ConflictException;
import example.user.domain.UserRepository;
import example.user.service.request.UserSignUpServiceRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@ActiveProfiles("test")
@SpringBootTest
class AuthFacadeTest {
    @Autowired
    AuthFacade authFacade;
    @Autowired
    UserRepository userRepository;
    @Value("${oauth.kakao.test}")
    String accessToken;

    @AfterEach
    void afterEach() {
        userRepository.deleteAllInBatch();
    }

    @DisplayName("동일한 사용자의 중복된 회원 가입 요청이 들어와도 한 번만 회원가입된다.")
    @Test
    void signUpConcurrencyTest() throws InterruptedException {
        // given
        int threadCount = 20;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        UserSignUpServiceRequest request = new UserSignUpServiceRequest("Test", "kakao", accessToken);
        long beforeCount = userRepository.count();

        // when
        IntStream.range(0, threadCount)
                .forEach(i -> executorService.submit(() -> {
                    try {
                        authFacade.signUp(request);
                    } catch (ConflictException e) {
                        e.printStackTrace();
                        throw e;
                    } finally {
                        latch.countDown();
                    }
                }));
        latch.await();

        // then
        long afterCount = userRepository.count();
        Assertions.assertThat(afterCount - beforeCount).isEqualTo(1L);
    }
}