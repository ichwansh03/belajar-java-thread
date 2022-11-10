import org.junit.jupiter.api.Test;

public class ThreadCommunicationTest {

    private String message = null;

    @Test
    void waitNotify() throws InterruptedException {

        final var lock = new Object();

        var thread1 = new Thread(() -> {
            synchronized (lock){
                try {
                    //tidak akan berhenti jika call notify
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        var thread2 = new Thread(() -> {
           synchronized (lock){
               message = "Testing";
               lock.notify();
           }
        });

        thread1.start();
        thread2.start();

        thread2.join();
        thread1.join();
    }

    @Test
    void waitNotifyAll() throws InterruptedException {

        final var lock = new Object();

        var thread1 = new Thread(() -> {
            synchronized (lock){
                try {
                    //tidak akan berhenti jika call notify
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        var thread3 = new Thread(() -> {
            synchronized (lock){
                try {
                    //tidak akan berhenti jika call notify
                    lock.wait();
                    System.out.println(message);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        var thread2 = new Thread(() -> {
            synchronized (lock){
                message = "Testing";
                //buat jalanin semua thread
                lock.notifyAll();
            }
        });

        thread1.start();
        thread3.start();

        thread2.start();

        thread2.join();
        thread1.join();
        thread3.join();
    }
}
