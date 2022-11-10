import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

    @Test
    void delayedJob() throws InterruptedException {
        var task = new TimerTask(){
            @Override
            public void run() {
                System.out.println("job delay");
            }
        };

        var timer = new Timer();
        //dieksekusi setiap 2 detik, jika tanpa period task hanya dieksekusi 1x
        timer.schedule(task, 2000, 2000);

        Thread.sleep(10_000L);
    }
}
