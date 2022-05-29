package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class NewsApi {
    //AppKey from NewsApi login
    private final String apiKey = "6993410ad3df42c89bbb4c8b3b015172";

    //execute the get Request of specific url and return response
    //Exception - alle
    private String doGetRequest(URL url) throws NewsApiExceptions {
        //create a client
        OkHttpClient client = new OkHttpClient();
        //create a request
        Request request = new Request.Builder()
                .url(url)
                .build();
        //create response by executing the request through client
        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 429) {
                throw new NewsApiExceptions("Too Many Requests. You made too many requests within a window of time and have been rate limited. Back off for a while.");
            } else if (response.code() == 500) {
                throw new NewsApiExceptions("Server Error. Something went wrong on the NewsApi.org side.");
            } else {
                return Objects.requireNonNull(response.body()).string();
            }
        } catch (IOException e) {
            throw new NewsApiExceptions(e);
        }
    }

    public void downloadFileSync(String downloadUrl) throws NewsApiExceptions {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        try {
            Response response = client.newCall(request).execute();
            FileOutputStream fos = new FileOutputStream("C:\\test\\text.txt");
            fos.write(response.body().bytes());
            fos.close();
        } catch (IOException e) {
            throw new NewsApiExceptions(e);
        }
    }

    //create Start of url which is has the same scheme for all request
    private void createStart(HttpUrl.Builder builder, Endpoint endpoint, String query) {
        builder
                //add scheme
                .scheme("https")
                //add host
                .host("newsapi.org")
                //add path (either everything or top-headlines)
                .addPathSegments("v2/" + endpoint.getEndpoint())
                //add query
                .addQueryParameter("q", query)
                .addQueryParameter("apiKey", apiKey);
    }

    //create and return specific url for top-headlines
    public URL buildUrlTop(Endpoint endpoint, String query, Country country, Category category) {
        //Create URL Builder
        HttpUrl.Builder builder = new HttpUrl.Builder();
        //add start of url
        createStart(builder, endpoint, query);
        //add specific query parameters
        builder
                .addQueryParameter("country", country.getCountry())
                .addQueryParameter("category", category.getCategory());
        //add ApiKey and build full URL
        return builder.build().url();
    }

    //create and return specific url for everything
    public URL buildUrlEverything(Endpoint endpoint, String query, Language language, SortBy sortBy) {
        //if query is null or empty null gets returned
        //Create URL Builder
        HttpUrl.Builder builder = new HttpUrl.Builder();
        //add start of url
        createStart(builder, endpoint, query);
        //add specific query parameters
        builder
                .addQueryParameter("language", language.getLanguage())
                //.addQueryParameter("country", "at")
                .addQueryParameter("sortBy", sortBy.getSort());
        //add ApiKey and build full URL
        return builder.build().url();
    }

    //Exception/try and catch - alle
    public NewsResponse getResponse(URL url) throws NewsApiExceptions {
        NewsResponse responses;
        //get response from specific url
        String response = doGetRequest(url);
        //map response values (json values) to NewsResponse Object variables via Jackson
        try {
            responses = new ObjectMapper().readValue(response, NewsResponse.class);
        } catch (JsonProcessingException e) {
            throw new NewsApiExceptions(e);
        }

        if (responses.getStatus().equals("error")) {
            throw new NewsApiExceptions(responses.getMessage());
        }
        //return filled NewsResponse Object
        return responses;
    }
}

