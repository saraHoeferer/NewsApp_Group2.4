package at.ac.fhcampuswien;

import java.util.List;

//Class from Solution of Exercise 3 from Leons Github
public class SequentialDownloader extends Downloader {

    // returns number of downloaded article urls
    @Override
    public int process(List<String> urls) throws NewsApiExceptions{
        int count = 0;
        for (String url : urls) {
            try {
                String fileName = saveUrl2File(url);
                if(fileName != null)
                    count++;
            } catch (NewsApiExceptions e){
                System.err.println(e.getMessage());
                throw new NewsApiExceptions(e.getMessage());
            } catch (Exception e){
                throw new NewsApiExceptions("Different problem occurred in " + this.getClass().getName() + ". Message: " + e.getMessage());
            }
        }
        return count;
    }
}