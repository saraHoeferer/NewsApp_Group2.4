package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppController {
    //instance variable
    private static AppController instance = null;
    private NewsResponse response;
    private final NewsApi api;

    //constructor
    private AppController() {
        api = NewsApi.getInstance();
    }

    public static AppController getInstance(){
        if (instance == null){
            instance = new AppController();
        }
        return instance;
    }

    //get amount of articles - Sara
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

    public int downloadURLs(Downloader downloader) throws NewsApiExceptions{
        if(response == null)
            throw new NewsApiExceptions("Something went wrong");

        List<String> urls = new ArrayList<>();

        Stream<Article> streamofArticle = response.getArticles().stream();
        urls = streamofArticle
                .flatMap(Article->Stream.of(Article.getUrl(), Article.getUrlToImage()))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return downloader.process(urls);
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

    //function to manage download of files, throw NewsApiException from downloadFileURL
    public void downloadFile(String url, String dirPath) throws NewsApiExceptions {
        //download url from article into file
        api.downloadFileURL(url, dirPath);
        //cut parts of html so article gets more readable
        manipulateFile(url, dirPath);
    }

    //function to cut parts form html text file,  throw NewsApiException because of IO Exceptions
    public void manipulateFile(String url, String dirPath) throws NewsApiExceptions {
        //get path of file
        Path textfilePath = Paths.get(dirPath);
        File f = new File(dirPath);

        //if file doesn't exist
        if (!f.exists()) {
            //try to create file
            try {
                Files.createFile(textfilePath);
            } catch (IOException e){
                //or throw exception
                throw new NewsApiExceptions(e.getMessage());
            }
        }

        //create writer and doc
        FileWriter writer;
        Document doc;
        try {
            writer = new FileWriter(f);
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new NewsApiExceptions(e.getMessage());
        }

        //cut links an images and save paragraphs and headlines
        doc.select("a").remove();
        doc.select("img").remove();
        Elements p = doc.select("p");
        Elements h1 = doc.select("h1");
        Elements h2 = doc.select("h2");

        //try to write those into file
        try {
            writer.write(h1.text());
            writer.write(System.getProperty("line.separator"));
            writer.write(h2.text());
            writer.write(System.getProperty("line.separator"));
            writer.write(p.text());
            writer.close();
        } catch (IOException e) {
            //else throw Exception
            throw new NewsApiExceptions(e.getMessage());
        }
    }


}
