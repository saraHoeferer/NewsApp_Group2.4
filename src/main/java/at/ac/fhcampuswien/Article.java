package at.ac.fhcampuswien;

public class Article {

    //instance variables
    private String author;
    private String title;
    private String description;
    private String content;
    private Source source;
    private String publishedAt;
    private String url;
    private String urlToImage;

    //Getter and Setter (Setter important for Jackson)
    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author){
        if (author == null){
            this.author = "No Author";
        } else {
            this.author = author;
        }
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl (String url){
        this.url = url;
    }

    public String getPublishedAt(){
        return publishedAt;
    }

    public void setPublishedAt (String publishedAt){
        this.publishedAt = publishedAt.substring(0, publishedAt.indexOf("T"));
    }

    public Source getSource(){
        return source;
    }

    public void setSource (Source source){
        this.source = source;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription (String description){
        this.description = description;
    }

    public String getUrlToImage(){
        return urlToImage;
    }

    public void setUrlToImage (String urlToImage){
        this.urlToImage = urlToImage;
    }

    public String getContent(){
        return content;
    }

    public void setContent (String content){
        this.content = content;
    }

    //Override to string method so String looks like "Title: X, Author: X, Published at: X, URL"
    @Override
    public String toString(){
        return "Title: " + title + "\nAuthor: " + author + ", Published at: "+ publishedAt + "\n" + url + "\nContent: " + content;
    }
}
