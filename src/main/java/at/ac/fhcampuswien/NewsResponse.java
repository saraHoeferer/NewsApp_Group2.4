package at.ac.fhcampuswien;

import java.util.List;

public class NewsResponse {
    public String status;
    public int totalResults;
    public List <Article> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {return articles;}
}
