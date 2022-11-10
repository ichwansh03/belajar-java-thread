import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.concurrent.*;

public class BlockingQueueTest {

    @Test
    void arryaBlockingQueue() throws InterruptedException {
        final var queue = new ArrayBlockingQueue<String>(5);
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    queue.put("data");
                    System.out.println("finish put data");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true){
                //last in first out: nunggu thread atas keluar jika thread > n
                try {
                    Thread.sleep(2000);
                    var value = queue.take();
                    System.out.println("receive data = "+value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    void linkedBlockingQueue() throws InterruptedException {
        final var queue = new LinkedBlockingQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                try {
                    queue.put("data");
                    System.out.println("finish put data");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true){
                //tidak perlu menunggu put karena linked sifatnya dinamis
                try {
                    Thread.sleep(2000);
                    var value = queue.take();
                    System.out.println("receive data = "+value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    void priorityBlockingQueue() throws InterruptedException {
        final var queue = new PriorityBlockingQueue<Integer>(10, Comparator.reverseOrder());
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                queue.put(index);
                System.out.println("finish put data");
            });
        }

        executor.execute(() -> {
            while (true){
                //reverse order: dibalik pemanggilan datanya
                try {
                    Thread.sleep(2000);
                    var value = queue.take();
                    System.out.println("receive data = "+value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    void delayedQueue() throws InterruptedException {
        final var queue = new DelayQueue<ScheduledFuture<String>>();
        final var executor = Executors.newFixedThreadPool(20);
        final var executorScheduled = Executors.newScheduledThreadPool(10);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            queue.put(executorScheduled.schedule(() -> "Data "+index, i, TimeUnit.SECONDS));
        }

        executor.execute(() -> {
            while (true){
                //ngambil data satu persatu dari delay
                try {
                    var value = queue.take();
                    System.out.println("receive data = "+value.get());
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    void SynchronousQueue() throws InterruptedException {
        final var queue = new SynchronousQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    queue.put("data-"+index);
                    System.out.println("finish put data "+index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true){
                //dieksekusi ketika ada yg ngambil data
                try {
                    Thread.sleep(2000);
                    var value = queue.take();
                    System.out.println("receive data = "+value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    void blockingDeque() throws InterruptedException {
        final var queue = new LinkedBlockingDeque<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    queue.put("data-"+index);
                    System.out.println("finish put data "+index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true){
                //bebas mau nentuin pake FIFO atau LIFO
                try {
                    Thread.sleep(2000);
                    //diambil yang paling terakhir
                    var value = queue.takeLast();
                    System.out.println("receive data = "+value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    void transferQueue() throws InterruptedException {
        final var queue = new LinkedTransferQueue<String>();
        final var executor = Executors.newFixedThreadPool(20);

        for (int i = 0; i < 10; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    queue.transfer("data-"+index);
                    System.out.println("finish put data "+index);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.execute(() -> {
            while (true){
                try {
                    Thread.sleep(2000);
                    var value = queue.take();
                    System.out.println("receive data = "+value);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }
}
