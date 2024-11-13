import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultithreadedCounter {
    public static void main(String[] args) {

        int numberOfThreads = 1000;
        int countRange = 1000000;
        int numbersPerThread = countRange / numberOfThreads;



        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<Integer>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < numberOfThreads; i++) {
            Future<Integer> future = executorService.submit(new Callable<Integer>() {

                @Override
                public Integer call() {
                    int count = 0;
                    for (int j = 1; j <= numbersPerThread; j++) {
                        count++;
                    }
                    return count;
                }
            });

            futures.add(future);

        }

        int totalSum = 0;

        for (Future<Integer> future : futures) {

            try {
                totalSum += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }


        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        executorService.shutdown();

        System.out.println("Total count across all threads: " + totalSum);
        System.out.println("Time taken: " + duration + " milliseconds");
    }
}
