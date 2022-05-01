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

    //get amount of articles
    public int getArticleCount () throws IOException {
        int count = 0;

        //get articles TopHeadlines
        articles = getTopHeadlinesAustria();
        if (articles != null) {
            count += articles.size();
        }

        //get articles bitcoin
        articles = getAllNewsBitcoin();
        if (articles != null) {
            count += articles.size();
        }

        //return sum
        return count;
    }

    //getter for article list
    public List <Article> getArticles(){
        return articles;
    }

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

    //return all news about bitcoin
    public List <Article> getAllNewsBitcoin () throws IOException {
        //build specific url for Endpoint Everything
        URL url = api.buildUrlEverything(Endpoint.EVERYTHING, "bitcoin", Language.GERMAN, SortBy.NONE);
        //if url null (because query is empty or null
        if (url == null){
            //set articles to null
            articles = null;
        } else {
            //get response
            response = api.getResponse(url);
            //set articles list form articles of response
            articles = response.getArticles();
        }
        return articles;
    }


}
