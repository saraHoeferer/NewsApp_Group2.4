package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppController {
    //instance variable
    private NewsResponse response;
    private final NewsApi api;

    //Singleton Pattern - because we want only one instance of AppController because it uses NewsApi and therefore also sends requests
    //and we don't have multiple instances which all can make Requests to Api

    //private instance which is null at the beginning
    private static AppController instance = null;

    //Private Constructor
    private AppController() {
        api = NewsApi.getInstance();
    }

    //Public method getInstance
    public static AppController getInstance(){
        //if instance is null, if no instance is there
        if (instance == null){
            //make new instance
            instance = new AppController();
        }
        //else return existed instance
        return instance;
    }

    //Method from Solution of Exercise 3 from Leon's Github
    public int downloadURLs(Downloader downloader) throws NewsApiExceptions{
        if(response == null)
            throw new NewsApiExceptions("Something went wrong");

        List<String> urls = new ArrayList<>();

        //Get Urls from Stream of Article List
        Stream<Article> streamofArticle = response.getArticles().stream();
        urls = streamofArticle
                //get only Urls from article as well as from Urls form images
                .flatMap(Article->Stream.of(Article.getUrl(), Article.getUrlToImage()))
                //only get those objects were the url is not null
                .filter(Objects::nonNull)
                //collect them into url list
                .collect(Collectors.toList());

        //hand over list of urls to process method of whatever downloade is used
        return downloader.process(urls);
    }

    //get amount of articles
    public int getArticleCount() {
        int count = 0;
        //if response is not null
        if (response != null) {
            //count how many article response has
            count += response.getArticles().size();
        }
        //return sum
        return count;
    }

    //get all news, throw NewsApiException from getResponse
    public NewsResponse getTopHeadlinesAustria() throws NewsApiExceptions {
        //build specific url for endpoint Top-Headlines
        URL url = api.buildUrlTop(Endpoint.TOPHEADLINES, "", Country.AUSTRIA, Category.NONE);
        //get response
        response = api.getResponse(url);
        //return response
        return response;
    }

    //get all news about bitcoin,  throw NewsApiException from getResponse
    public NewsResponse getAllNewsBitcoin() throws NewsApiExceptions {
        //build specific url for Endpoint Everything
        URL url = api.buildUrlEverything(Endpoint.EVERYTHING, "bitcoin", Language.NONE, SortBy.NONE);
        //get response
        response = api.getResponse(url);
        //return response
        return response;
    }

    //get individual request for endpoint Everything, throw NewsApiException from getResponse
    public NewsResponse getYourNewsEverything(Endpoint endpoint, String query, Language language, SortBy sortBy) throws NewsApiExceptions {
        //build url
        URL url = api.buildUrlEverything(endpoint, query, language, sortBy);
        //get response
        response = api.getResponse(url);
        //return response
        return response;

    }

    //get individual request for endpoint TopHeadlines, throw NewsApiException from getResponse
    public NewsResponse getYourNewsTop(Endpoint endpoint, String query, Country country, Category category) throws NewsApiExceptions {
        //build url
        URL url = api.buildUrlTop(endpoint, query, country, category);
        //get response
        response = api.getResponse(url);
        //return response
        return response;
    }
}
