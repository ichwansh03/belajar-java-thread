import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.*;

public class CompletableFutureTest {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    private Random random = new Random();

    //membuat future secara manual
    public CompletableFuture<String> getValue(){
        CompletableFuture<String> future = new CompletableFuture<>();

        executorService.execute(() -> {
            try {
                Thread.sleep(2000);
                future.complete("Ichwan Sholihin");
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });

        return future;
    }

    //langsung execute tanpa harus menunggu data
    @Test
    void completionStage() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = getValue();

        CompletableFuture<String[]> future2 = future.thenApply(string -> string.toUpperCase())
                .thenApply(string -> string.toUpperCase())
                .thenApply(string -> string.split(" "));

        String[] strings = future2.get();
        for (var value: strings){
            System.out.println(value);
        }
    }

    @Test
    void create() throws ExecutionException, InterruptedException {
        Future<String> future = getValue();
        System.out.println(future.get());
    }

    private void execute(CompletableFuture<String> future, String value){
        executorService.execute(() -> {
            try {
                Thread.sleep(1000, random.nextInt(5000));
                future.complete(value);
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
            }
        });
    }

    public Future<String> getFastest(){
        CompletableFuture<String> future = new CompletableFuture<>();

        execute(future, "thread 1");
        execute(future, "thread 2");
        execute(future, "thread 3");

        return future;
    }

    @Test
    void testFastest() throws ExecutionException, InterruptedException {
        System.out.println(getFastest().get());
    }

}
