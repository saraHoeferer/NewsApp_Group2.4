package at.ac.fhcampuswien;

import java.util.List;

public class NewsResponse {
    //instance variables
    private String status; //Did response work?
    private int totalResults; //How many articles?
    private List <Article> articles; //List of responded articles

    //Getter and Setter (Setter important for Jackson)
    public String getStatus() {
        return status;
    }

    public void setStatus (String status) {this.status = status;}

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {this.totalResults = totalResults;}

    public List<Article> getArticles() {return articles;}

    public void setArticles (List <Article> articles) {this.articles = articles;}
}
