package at.ac.fhcampuswien;

public class NewsApiExceptions extends Exception{
    NewsApiExceptions(){
        super("Da ist etwas bei der NewsApi schiefgelaufen!");
    }

    NewsApiExceptions (String message){
        super(message);
    }
}
