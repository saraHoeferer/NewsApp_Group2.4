package at.ac.fhcampuswien;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ParallelDownloader extends Downloader{

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls) throws NewsApiExceptions {
        // Hint: use ExecutorService with Callables
        int numWorkers = Runtime.getRuntime().availableProcessors();
        ExecutorService pool = Executors.newFixedThreadPool(numWorkers);

        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < urls.size(); i++){
            int idx = i;
            Callable<String> task = () -> saveUrl2File(urls.get(idx));
            futures.add(pool.submit(task));
        }

        List<String> results = new ArrayList<>();
        for (Future<String> result: futures){
            try {
                if (result.get() != null){
                    results.add(result.get());
                }
            } catch (InterruptedException e) {
               throw new NewsApiExceptions(e.getMessage());
            } catch (ExecutionException e) {
                throw new NewsApiExceptions(e.getMessage());
            }
        }
        return results.size();
    }
}
