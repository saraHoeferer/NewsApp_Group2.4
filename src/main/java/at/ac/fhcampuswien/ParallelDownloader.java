package at.ac.fhcampuswien;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

//class from Solution of Exercise 3 from Leon Github --> but implemented the process function
public class ParallelDownloader extends Downloader{

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls) throws NewsApiExceptions {
        // Hint: use ExecutorService with Callables
        //get all threads that are available at Runtime
        int numWorkers = Runtime.getRuntime().availableProcessors();
        //set pool which has all available threads at Runtime
        ExecutorService pool = Executors.newFixedThreadPool(numWorkers);

        //set new Future List
        List<Future<String>> futures = new ArrayList<>();

        //as long as the urlList goes
        for (int i = 0; i < urls.size(); i++){
            //make index variable to use in Lambda Expression
            int idx = i;
            //make callable list as all results from download function of all urls
            Callable<String> task = () -> saveUrl2File(urls.get(idx));
            //add submitted tasks to future pool
            futures.add(pool.submit(task));
        }

        //make new result list
        List<String> results = new ArrayList<>();
        //for all submitted task in future list
        for (Future<String> result: futures){
            try {
                //try to get result and check if null
                if (result.get() != null){
                    //if not null then add it into result list
                    results.add(result.get());
                }
            //else catch errors and throw them as NewApi Exceptions
            } catch (InterruptedException e) {
               throw new NewsApiExceptions(e.getMessage());
            } catch (ExecutionException e) {
                throw new NewsApiExceptions(e.getMessage());
            }
        }
        //return size of result list --> so amount of all actually downloaded Urls
        return results.size();
    }
}
