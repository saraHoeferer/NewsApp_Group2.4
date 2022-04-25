package at.ac.fhcampuswien;

public class Article {

    //instance variables
    public String author;
    public String title;
    public String url;
    public String publishedAt;

    //constructor
    public Article(String author, String title) {
        this.author = author;
        this.title = title;
    }

    //Getter
    public String getAuthor(){
        return author;
    }

    public String getTitle(){
        return title;
    }

    //Override to string method so String looks like "Title: X, Author: X"
    @Override
    public String toString(){
        if (author == null){
            author = "No Author";
        }

        publishedAt = publishedAt.substring(0, publishedAt.indexOf("T"));

        return "Title: " + title + ", Author: " + author + "\nURL: " + url + "\nPublished at: " +publishedAt;
    }
}
