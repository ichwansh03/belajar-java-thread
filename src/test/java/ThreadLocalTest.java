import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalTest {

    //gunanya thread local agar tidak ada data yg duplicate
    @Test
    void threadLocalTest() throws InterruptedException {
        final var random = new Random();
        final var userService = new UserService();
        final var executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 50; i++) {
            final var index = i;
            executor.execute(() -> {

                try {
                    userService.setUser("user-"+index);
                    Thread.sleep(1000 + random.nextInt(1000));
                    userService.doAction();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.awaitTermination(1, TimeUnit.HOURS);
    }
}
