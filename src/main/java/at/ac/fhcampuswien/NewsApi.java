package at.ac.fhcampuswien;

import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URL;


public class NewsApi {
    private final String appKey = "6993410ad3df42c89bbb4c8b3b015172";

    private String doGetRequest(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public URL buildUrlTop(String query, String country, String category){
        URL url = new HttpUrl.Builder()
                .scheme("https")
                .host("newsapi.org")
                .addPathSegment("v2")
                .addPathSegment("top-headlines")
                .addQueryParameter("q", query)
                .addQueryParameter("country", country)
                .addQueryParameter("category", category)
                .addQueryParameter("apiKey", appKey)
                .build().url();
        return url;
    }

    public URL buildUrlEverything (String query, String language, String sortBy){
        URL url = new HttpUrl.Builder()
                .scheme("https")
                .host("newsapi.org")
                .addPathSegment("v2")
                .addPathSegment("everything")
                .addQueryParameter("q", query)
                .addQueryParameter("language", language)
                .addQueryParameter("sortBy", sortBy)
                .addQueryParameter("apiKey", appKey)
                .build().url();
        return url;
    }

    public NewsResponse getResponse (URL url) throws IOException {
        Gson gson = new Gson();
        String response = doGetRequest(url);
        NewsResponse responesArray = gson.fromJson(response, NewsResponse.class);
        return responesArray;
    }
}

