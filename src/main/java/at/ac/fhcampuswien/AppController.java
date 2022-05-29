package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class AppController {
    //instance variable
    private NewsResponse response;
    private final NewsApi api;

    //constructor
    public AppController() {
        api = new NewsApi();
    }

    //Exception
    //get amount of articles - Sara
    public int getArticleCount() throws NewsApiExceptions {
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

    //Exception - Sophia
    //get all news
    public NewsResponse getTopHeadlinesAustria() throws NewsApiExceptions {
        //build specific url for endpoint Top-Headlines
        URL url = api.buildUrlTop(Endpoint.TOPHEADLINES, "", Country.AUSTRIA, Category.NONE);
        //get response
        response = api.getResponse(url);
        return response;
    }

    //Exception - Chrisi
    //return all news about bitcoin
    public NewsResponse getAllNewsBitcoin() throws NewsApiExceptions {
        //build specific url for Endpoint Everything
        URL url = api.buildUrlEverything(Endpoint.EVERYTHING, "bitcoin", Language.NONE, SortBy.NONE);
        //get response
        response = api.getResponse(url);
        return response;
    }

    public NewsResponse getYourNewsEverything(Endpoint endpoint, String query, Language language, SortBy sortBy) throws NewsApiExceptions {
        URL url = api.buildUrlEverything(endpoint, query, language, sortBy);
        response = api.getResponse(url);
        return response;

    }

    public NewsResponse getYourNewsTop(Endpoint endpoint, String query, Country country, Category category) throws NewsApiExceptions {
        URL url = api.buildUrlTop(endpoint, query, country, category);
        response = api.getResponse(url);
        return response;
    }

    public void downloadFile(String url) throws NewsApiExceptions {
        api.downloadFileSync(url);
        manipulateFile(url);
    }

    public void manipulateFile(String url) throws NewsApiExceptions {
        File f = new File("C:\\test\\text.txt");
        FileWriter writer;
        Document doc;
        try {
            writer = new FileWriter(f);
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new NewsApiExceptions(e);
        }

        doc.select("a").remove();
        doc.select("img").remove();
        Elements p = doc.select("p");
        Elements h1 = doc.select("h1");
        Elements h2 = doc.select("h2");

        try {
            writer.write(h1.text());
            writer.write(System.getProperty("line.separator"));
            writer.write(h2.text());
            writer.write(System.getProperty("line.separator"));
            writer.write(p.text());
            writer.close();
        } catch (IOException e) {
            throw new NewsApiExceptions(e);
        }
    }


}
