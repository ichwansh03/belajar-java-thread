import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FutureTest {

    @Test
    void testFuture() throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();

        //running task untuk mengembalikan nilai gunakan callable
        Callable<String> callable = () -> {
            Thread.sleep(3000);
            return "ichwan";
        };

        Future<String> future = executor.submit(callable);
        System.out.println("end");

        //cek untuk menunggu data
        while (!future.isDone()){
            System.out.println("waiting future");
            Thread.sleep(1000);
        }

        //mengeblok selama 3 detik
        String value = future.get();
        System.out.println(value);
    }

    @Test
    void testFutureCancelled() throws ExecutionException, InterruptedException {
        var executor = Executors.newSingleThreadExecutor();

        //running task untuk mengembalikan nilai gunakan callable
        Callable<String> callable = () -> {
            Thread.sleep(3000);
            return "ichwan";
        };

        Future<String> future = executor.submit(callable);
        System.out.println("end");

        //membatalkan ketika 2 detik dijalankan
        Thread.sleep(2000);
        future.cancel(true);

        //mengeblok selama 3 detik
        String value = future.get();
        System.out.println(value);
    }

    //tunggu semua selesai lalu get
    @Test
    void invokeAll() throws InterruptedException, ExecutionException {
        var executor = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = IntStream.range(1,11).mapToObj(value -> (Callable<String>) () -> {
            Thread.sleep(value * 500L);
            return String.valueOf(value);
        }).collect(Collectors.toList());

        var futures = executor.invokeAll(callables);

        for (Future<String> stringFuture : futures){
            System.out.println(stringFuture.get());
        }
    }

    //paling cepat diambil
    @Test
    void invokeAny() throws InterruptedException, ExecutionException {
        var executor = Executors.newFixedThreadPool(10);

        List<Callable<String>> callables = IntStream.range(1,11).mapToObj(value -> (Callable<String>) () -> {
            Thread.sleep(value * 500L);
            return String.valueOf(value);
        }).collect(Collectors.toList());

        var futures = executor.invokeAny(callables);
        System.out.println(futures);
    }
}
