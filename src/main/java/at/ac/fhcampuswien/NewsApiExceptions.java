package at.ac.fhcampuswien;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.net.PortUnreachableException;
import java.net.UnknownHostException;

public class NewsApiExceptions extends Exception {
    NewsApiExceptions() { super("Da ist etwas schiefgelaufen!"); }

    NewsApiExceptions(String message) {
        super(message);
    }

    NewsApiExceptions (PortUnreachableException e) { super(e.getMessage()); }

    NewsApiExceptions(UnknownHostException e){ super(e.getMessage()); }

    NewsApiExceptions(IOException e) {
        super(e.getMessage());
    }

    NewsApiExceptions(JsonProcessingException e) {
        super(e.getMessage());
    }
}
