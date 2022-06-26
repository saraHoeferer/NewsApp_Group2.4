package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.PortUnreachableException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;

public class NewsApi {
    //AppKey from NewsApi login
    private final String apiKey = "6993410ad3df42c89bbb4c8b3b015172";

    //Singleton Pattern - because we want only one instance of NewsApi so only this one send request
    //and we don't have multiple instances which all can make Requests to Api

    //private instance which is null at the beginning
    private static NewsApi instance = null;

    //Private Constructor
    private NewsApi(){}

    //Public method getInstance
    public static NewsApi getInstance(){
        //if instance is null, if no instance is there
        if (instance == null){
            //make new instance
            instance = new NewsApi();
        }
        //else return existed instance;
        return instance;
    }

    //execute the get Request of specific url and return response
    private String doGetRequest(URL url) throws NewsApiExceptions {
        //create a client
        OkHttpClient client = new OkHttpClient();
        //create a request
        Request request = new Request.Builder()
                .url(url)
                .build();

        //try to get response
        try {
            //create response by executing the request through client
            Response response = client.newCall(request).execute();
            //throw exception due to response codes from NewsApi
            if (response.code() == 429) {
                throw new NewsApiExceptions("Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while.");
            } else if (response.code() == 500) {
                throw new NewsApiExceptions("Server Error. Something went wrong on the NewsApi.org side.");
            } else {
                return Objects.requireNonNull(response.body()).string();
            }
        //catch different IO Exception and throw them as NewsApiExceptions
        } catch (UnknownHostException e){
            throw new NewsApiExceptions(e.getMessage());
        }  catch (PortUnreachableException e){
            throw new NewsApiExceptions(e.getMessage());
        }  catch (IOException e) {
            throw new NewsApiExceptions(e.getMessage());
        }
    }

    //create Start of url which is has the same scheme for all request
    //use own URLBuilder
    private void createStart(URLBuilder builder, Endpoint endpoint, String query) {
        builder
                //add scheme
                .scheme("https")
                //add host
                .host("newsapi.org")
                //add path (either everything or top-headlines)
                .pathSegment("v2/" + endpoint.getEndpoint())
                //add query
                .queryParameter("q", query)
                .queryParameter("apiKey", apiKey);
    }

    //create and return specific url for top-headlines
    //use own URLBuilder
    public URL buildUrlTop(Endpoint endpoint, String query, Country country, Category category) throws NewsApiExceptions {
        //Create URL Builder
        URLBuilder builder = new URLBuilder();
        //add start of url
        createStart(builder, endpoint, query);
        //add specific query parameters
        builder
                .queryParameter("country", country.getCountry())
                .queryParameter("category", category.getCategory());

        //add ApiKey and build full URL
        try {
            return builder.build();
        } catch (MalformedURLException e){
            throw new NewsApiExceptions(e.getMessage());
        }
    }

    //create and return specific url for everything
    //use own URLBuilder
    public URL buildUrlEverything(Endpoint endpoint, String query, Language language, SortBy sortBy) throws NewsApiExceptions {
        //if query is null or empty null gets returned
        //Create URL Builder
        URLBuilder builder = new URLBuilder();
        //add start of url
        createStart(builder, endpoint, query);
        //add specific query parameters
        builder
                .queryParameter("language", language.getLanguage())
                //.addQueryParameter("country", "at")
                .queryParameter("sortBy", sortBy.getSort());
        //add ApiKey and build full URL
        try {
            return builder.build();
        } catch (MalformedURLException e){
            throw new NewsApiExceptions(e.getMessage());
        }
    }

    //get Response and map it as NewsResponse Object
    public NewsResponse getResponse(URL url) throws NewsApiExceptions {
        NewsResponse responses;
        //get response from specific url
        String response = doGetRequest(url);

        //try to map response as NewsResponse Object
        try {
            //map response values (json values) to NewsResponse Object variables via Jackson
            responses = new ObjectMapper().readValue(response, NewsResponse.class);
        //catch Json Exception and throw it as NewsApiException
        } catch (JsonProcessingException e) {
            throw new NewsApiExceptions(e.getMessage());
        }

        //if response comes back with status "error"
        if (responses.getStatus().equals("error")) {
            //throw NewsApiException with according message
            throw new NewsApiExceptions(responses.getMessage());
        }
        //return filled NewsResponse Object
        return responses;
    }
}

