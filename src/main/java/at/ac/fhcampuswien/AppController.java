package at.ac.fhcampuswien;

import javafx.scene.control.TableColumn;

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
        articles = getTopHeadlinesAustria();
        count += articles.size();
        articles = getAllNewsBitcoin();
        count += articles.size();

        return count;
    }

    //getter for article list
    public List <Article> getArticles(){
        return articles;
    }

    //get all news
    public List<Article> getTopHeadlinesAustria () throws IOException {
        URL url = api.buildUrlTop("", "au", "");
        response = api.getResponse(url);
        articles = response.getArticles();
        return articles;
    }

    //return all news about bitcoin
    public List <Article> getAllNewsBitcoin () throws IOException {
        URL url1 = api.buildUrlEverything("bitcoin", "de", "");
        response = api.getResponse(url1);
        articles = response.getArticles();
        return articles;
    }


}
