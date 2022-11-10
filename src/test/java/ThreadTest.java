import org.junit.jupiter.api.Test;

public class ThreadTest {

    @Test
    void thread(){
        var name = Thread.currentThread().getName();
        System.out.println(name);
    }

    //running thread secara terpisah
    @Test
    void createThread(){
        Runnable runnable = () -> {
            System.out.println("hello from thread "+Thread.currentThread().getName());
        };

        var thread = new Thread(runnable);
        thread.start();
    }

    //thread sleep: menjeda thread
    @Test
    void createThreadSleep() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2_000L);
                System.out.println("hello from thread "+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        var thread = new Thread(runnable);
        thread.start();

        System.out.println("End Program");

        //kalo mau liat thread yg stuck 2 detik
        Thread.sleep(3_000L);
    }

    //thread join: menunggu hingga proses thread selesai
    @Test
    void createThreadJoin() throws InterruptedException {
        Runnable runnable = () -> {
            try {
                Thread.sleep(2_000L);
                System.out.println("hello from thread "+Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        var thread = new Thread(runnable);
        thread.start();
        System.out.println("End Waiting");
        thread.join();
        System.out.println("End Program");

    }

    //thread interrupt: mengirim sinyal untuk menghentikan current job pada thread
    @Test
    void createThreadInterrupt() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println(Thread.currentThread().getState());
            for (int i = 0; i < 10; i++) {
                //manual cek interrupted
                if (Thread.interrupted()){
                    return;
                }
                System.out.println("Runnable "+i);
                try {
                    Thread.sleep(1_000L);
                } catch (InterruptedException e) {
                    return;
                }
            }
        };

        var thread = new Thread(runnable);
        System.out.println(thread.getState());
        thread.start();
        Thread.sleep(5_000);
        thread.interrupt();
        System.out.println("End Waiting");
        thread.join();
        System.out.println("End Program");
        System.out.println(thread.getState());
    }

    //ubah nama thread
    @Test
    void changeName(){
        var thread = new Thread(() -> {
            System.out.println("run in thread = "+Thread.currentThread().getName());
        });

        thread.setName("Ichwan");
        thread.start();
    }
}
