import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExchangerTest {

    //bertukar data antar thread
    @Test
    void test() throws InterruptedException {
        final var exchanger = new Exchanger<String>();
        final var executor = Executors.newFixedThreadPool(10);

        executor.execute(() -> {
            try {
                System.out.println("thread 1 send : first");
                Thread.sleep(1000);
                var result = exchanger.exchange("first");
                System.out.println("thread 1 receive : "+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.execute(() -> {
            try {
                System.out.println("thread 2 send : second");
                Thread.sleep(2000);
                var result = exchanger.exchange("second");
                System.out.println("thread 2 receive : "+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }
}
