import org.junit.jupiter.api.Test;

public class DeadLockTest {

    @Test
    void transfer() throws InterruptedException {
        var balance1 = new Balance(1_000_000L);
        var balance2 = new Balance(1_000_000L);

        var thread1 = new Thread(() -> {
            try {
                Balance.transfer(balance1,balance2,5000_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        var thread2 = new Thread(() -> {
            try {
                Balance.transfer(balance2,balance1,5000_000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("thread 1 = "+balance1.getValue());
        System.out.println("thread 2 = "+balance2.getValue());
    }
}