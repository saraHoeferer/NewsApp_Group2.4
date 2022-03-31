package at.ac.fhcampuswien;

public class Article {

    //instance variables
    private String author;
    private String title;

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
        String string = "Title: " + title + ", Author: " + author;
        return string;
    }
}
