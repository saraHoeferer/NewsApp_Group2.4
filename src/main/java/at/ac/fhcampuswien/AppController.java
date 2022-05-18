package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AppController {
    //instance variable
    private NewsResponse response;
    private List <Article> articles;
    private final NewsApi api;

    //constructor
    public AppController(){
        articles = new ArrayList<>();
        api = new NewsApi();
        response = new NewsResponse();
    }

    //Setter
    public void setArticles (List <Article> articles){ this.articles = articles; }

    //Exception
    //get amount of articles - Sara
    public int getArticleCount () throws IOException {
        int count = 0;

        //get articles TopHeadlines
        articles = getTopHeadlinesAustria();
        count += articles.size();

        //get articles bitcoin
        articles = getAllNewsBitcoin();
        count += articles.size();

        //return sum
        return count;
    }

    //getter for article list
    public List <Article> getArticles(){
        return articles;
    }

    //Exception - Sophia
    //get all news
    public List<Article> getTopHeadlinesAustria () throws IOException {
        //build specific url for endpoint Top-Headlines
        URL url = api.buildUrlTop(Endpoint.TOPHEADLINES, "", Country.AUSTRIA, Category.NONE);
        //get response
        response = api.getResponse(url);
        //set articles list form articles of response
        articles = response.getArticles();
        return articles;
    }

    //Exception - Chrisi
    //return all news about bitcoin
    public List <Article> getAllNewsBitcoin () throws IOException {
        //build specific url for Endpoint Everything
        URL url1 = api.buildUrlEverything(Endpoint.EVERYTHING, "bitcoin", Language.ENGLISH, SortBy.NONE);
        //get response
        response = api.getResponse(url1);
        //set articles list form articles of response
        articles = response.getArticles();
        return articles;
    }


}
