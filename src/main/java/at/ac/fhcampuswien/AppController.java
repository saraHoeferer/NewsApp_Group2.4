package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AppController {
    //instance variable
    private NewsResponse response;
    private List <Article> articles;
    private final NewsApi api;

    //constructor
    public AppController(){
        articles = new ArrayList<>();
        api = new NewsApi();
    }

    //Setter
    public void setArticles (List <Article> articles){ this.articles = articles; }

    //Exception
    //get amount of articles - Sara
    public int getArticleCount () throws IOException, NewsApiExceptions {
        int count = 0;

        //get articles TopHeadlines
        response = getTopHeadlinesAustria();
        if (response.getArticles() != null) {
            count += response.getArticles().size();
        }

        //get articles bitcoin
        response = getAllNewsBitcoin();
        if (response.getArticles() != null) {
            count += response.getArticles().size();
        }

        //return sum
        return count;
    }

    //getter for article list
    public List <Article> getArticles(){
        return articles;
    }

    //Exception - Sophia
    //get all news
    public NewsResponse getTopHeadlinesAustria () throws IOException, NewsApiExceptions {
        //build specific url for endpoint Top-Headlines
        URL url = api.buildUrlTop(Endpoint.TOPHEADLINES, "", Country.AUSTRIA, Category.NONE);
        /*
        try {
            response = api.getResponse(url)
        } catch (NewsApiExceptions e){
            throw new NewsApiException(response);
        } catch (IOException e) {
            throw new IOException();
        }
        */
        //get response
        response = api.getResponse(url);
        return response;
    }

    //Exception - Chrisi
    //return all news about bitcoin
    public NewsResponse getAllNewsBitcoin () throws IOException, NewsApiExceptions {
        //build specific url for Endpoint Everything
        URL url = api.buildUrlEverything(Endpoint.EVERYTHING, "bitcoin", Language.GERMAN, SortBy.NONE);
        //get response
        response = api.getResponse(url);
        return response;
    }


}
