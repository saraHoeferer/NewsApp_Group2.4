package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import java.net.URL;


public class NewsApi {

    //execute the get Request of specific url and return response
    //Exception - alle
    private String doGetRequest(URL url) throws IOException {
        //create a client
        OkHttpClient client = new OkHttpClient();
        //create a request
        Request request = new Request.Builder()
                .url(url)
                .build();
        //create response by executing the request through client
        Response response = client.newCall(request).execute();
        //return request body (content) as String (json)
        return response.body().string();
    }

    //create and return specific url
    public URL buildUrlTop(Endpoint endpoint, String query, Country country , Category category){
        //AppKey from NewsApi login
        String apiKey = "6993410ad3df42c89bbb4c8b3b015172";

        //create new URL Builder
        URL url = new HttpUrl.Builder()
                //add scheme
                .scheme("https")
                //add host
                .host("newsapi.org")
                //add Path
                .addPathSegment("v2")
                .addPathSegment(endpoint.getEndpoint())
                //add required/wished queries
                .addQueryParameter("q", query)
                .addQueryParameter("country", country.getCountry())
                .addQueryParameter("category", category.getCategory())
                //add apiKey (important, without apikey whole request doesn't work)
                .addQueryParameter("apiKey", apiKey)
                //build url
                .build().url();
        return url;
    }

    public URL buildUrlEverything(Endpoint endpoint, String query, Language language , SortBy sortBy){
        //AppKey from NewsApi login
        String apiKey = "6993410ad3df42c89bbb4c8b3b015172";

        if (query != null) {
            //create new URL Builder
            URL url = new HttpUrl.Builder()
                    //add scheme
                    .scheme("https")
                    //add host
                    .host("newsapi.org")
                    //add Path
                    .addPathSegment("v2")
                    .addPathSegment(endpoint.getEndpoint())
                    //add required/wished queries
                    .addQueryParameter("q", query)
                    .addQueryParameter("language", language.getLanguage())
                    .addQueryParameter("sortBy", sortBy.getSort())
                    //add apiKey (important, without apikey whole request doesn't work)
                    .addQueryParameter("apiKey", apiKey)
                    //build url
                    .build().url();
            return url;
        } else {
            return null;
        }
    }

    //Exception/try and catch - alle
    public NewsResponse getResponse (URL url) throws IOException {
        //get response from specific url
        String response = doGetRequest(url);
        //map response values (json values) to NewsResponse Object variables via Jackson
        NewsResponse responses = new ObjectMapper().readValue(response, NewsResponse.class);
        //return filled NewsResponse Object
        return responses;
    }
}

