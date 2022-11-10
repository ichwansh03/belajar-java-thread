import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    @Test
    void create() throws InterruptedException {

        var minThread = 10;
        var maxThread = 100;
        //berapa lama thread akan dihapus ketika tidak bekerja
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        //kapasitas antrian
        var queue = new ArrayBlockingQueue<Runnable>(100);

        var executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue);

        Runnable runnable = () -> {
            try {
                Thread.sleep(3000);
                System.out.println("runnable dari thread = "+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        executor.execute(runnable);

        Thread.sleep(5000);
    }

    @Test
    void execute() throws InterruptedException {

        var minThread = 10;
        var maxThread = 100;
        //berapa lama thread akan dihapus ketika tidak bekerja
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        //kapasitas antrian
        var queue = new ArrayBlockingQueue<Runnable>(100);

        var executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue);

        for (int i = 0; i < 150; i++) {
            final var task = i;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(3000);
                    System.out.println("runnable "+task+" dari thread = "+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(runnable);
        }

        //menunggu semua thread dijalankan
        executor.awaitTermination(1,TimeUnit.HOURS);

        //langsung dimatikan
        //executor.shutdown();
    }

    public static class LogRejectedExecutionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println("task "+r+" is rejected");

        }
    }

    //menghandle task yang melebihi capasity menggunakan RejectedHandler
    @Test
    void rejected() throws InterruptedException {

        var minThread = 10;
        var maxThread = 50;
        //berapa lama thread akan dihapus ketika tidak bekerja
        var alive = 1;
        var aliveTime = TimeUnit.MINUTES;
        //kapasitas antrian
        var queue = new ArrayBlockingQueue<Runnable>(10);
        var rejected = new LogRejectedExecutionHandler();
        var executor = new ThreadPoolExecutor(minThread, maxThread, alive, aliveTime, queue, rejected);

        for (int i = 0; i < 150; i++) {
            final var task = i;
            Runnable runnable = () -> {
                try {
                    Thread.sleep(3000);
                    System.out.println("runnable "+task+" dari thread = "+Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(runnable);
        }

        //menunggu semua thread dijalankan
        executor.awaitTermination(1,TimeUnit.HOURS);

        //langsung dimatikan
        //executor.shutdown();
    }
}
