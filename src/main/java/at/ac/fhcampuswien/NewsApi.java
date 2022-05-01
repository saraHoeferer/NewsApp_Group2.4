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
    //AppKey from NewsApi login
    private String apiKey = "6993410ad3df42c89bbb4c8b3b015172";

    //execute the get Request of specific url and return response
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

    //create Start of url which is has the same scheme for all request
    private void createStart (HttpUrl.Builder builder, Endpoint endpoint, String query){
        builder
                //add scheme
                .scheme("https")
                //add host
                .host("newsapi.org")
                //add path (either everything or top-headlines)
                .addPathSegments("v2/"+endpoint.getEndpoint())
                //add query
                .addQueryParameter("q", query);
    }

    //Create End of URL
    private URL createEnd (HttpUrl.Builder builder){
        //Add ApiKey and build to URL
        return builder.addQueryParameter("apiKey", apiKey).build().url();
    }

    //create and return specific url for top-headlines
    public URL buildUrlTop(Endpoint endpoint, String query, Country country , Category category){
        //Create URL Builder
        HttpUrl.Builder builder = new HttpUrl.Builder();
        //add start of url
        createStart(builder, endpoint, query);
        //add specific query parameters
        builder
                .addQueryParameter("country", country.getCountry())
                .addQueryParameter("category", category.getCategory());
        //add ApiKey and build full URL
        URL url = createEnd(builder);
        return url;
    }

    //create and return specific url for everything
    public URL buildUrlEverything(Endpoint endpoint, String query, Language language , SortBy sortBy){
        //if query is null or empty null gets returned
        if (query == null || query.equals("")) {
           return null;
        } else {
            //Create URL Builder
            HttpUrl.Builder builder = new HttpUrl.Builder();
            //add start of url
            createStart(builder, endpoint, query);
            //add specific query parameters
            builder
                    .addQueryParameter("language", language.getLanguage())
                    .addQueryParameter("sortBy", sortBy.getSort());
            //add ApiKey and build full URL
            URL url = createEnd(builder);
            return url;
        }
    }

    public NewsResponse getResponse (URL url) throws IOException {
        //get response from specific url
        String response = doGetRequest(url);
        //map response values (json values) to NewsResponse Object variables via Jackson
        NewsResponse responses = new ObjectMapper().readValue(response, NewsResponse.class);
        //return filled NewsResponse Object
        return responses;
    }
}

