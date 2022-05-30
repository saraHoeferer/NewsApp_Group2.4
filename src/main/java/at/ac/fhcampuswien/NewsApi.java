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
import java.net.PortUnreachableException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;

public class NewsApi {
    //AppKey from NewsApi login
    private final String apiKey = "6993410ad3df42c89bbb4c8b3b015172";

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

    //function to downloadFile
    public void downloadFileURL(String downloadUrl, String dirPath) throws NewsApiExceptions {
        //create new client
        OkHttpClient client = new OkHttpClient();
        //Make new request with url from certain article
        Request request = new Request.Builder().url(downloadUrl).build();
        try {
            //try to get response
            Response response = client.newCall(request).execute();
            //and create outputstream
            FileOutputStream fos = new FileOutputStream(dirPath);
            //and write
            fos.write(Objects.requireNonNull(response.body()).bytes());
            //and close
            fos.close();
        //catch IO Exception and throw it as NewsApiException
        } catch (IOException e) {
            throw new NewsApiExceptions(e.getMessage());
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

