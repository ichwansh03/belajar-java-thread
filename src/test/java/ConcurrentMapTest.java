import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentMapTest {

    @Test
    void test() throws InterruptedException {
        final var map = new ConcurrentHashMap<Integer, String>();
        final var executor = Executors.newFixedThreadPool(10);
        final var countDown = new CountDownLatch(100);

        for (int i = 0; i < 100; i++) {
            final var index = i;
            executor.execute(() -> {
                try {
                    Thread.sleep(1000);
                    map.putIfAbsent(index, "data-"+index);
                } catch (InterruptedException e){
                    e.printStackTrace();
                } finally {
                    countDown.countDown();
                }
            });
        }

        executor.execute(() -> {
            try{
                countDown.await();
                map.forEach((integer, s) -> System.out.println(integer + " : "+s));
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        });

        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    @Test
    void testCollection(){
        //konversi java collection
        List<String> list = List.of("Ichwan","Sholihin","Testing");
        List<String> synchronizedList = Collections.synchronizedList(list);

        System.out.println(synchronizedList);
    }
}
