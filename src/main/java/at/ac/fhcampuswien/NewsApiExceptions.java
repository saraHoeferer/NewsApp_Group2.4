package at.ac.fhcampuswien;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public class NewsApiExceptions extends Exception {
    NewsApiExceptions() {
        super("Da ist etwas schiefgelaufen!");
    }

    NewsApiExceptions(String message) {
        super(message);
    }

    NewsApiExceptions(IOException e) {
        super(e.getMessage());
    }

    NewsApiExceptions(JsonProcessingException e) {
        super(e.getMessage());
    }
}
