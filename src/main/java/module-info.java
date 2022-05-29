module at.ac.fhcampuswien{
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires org.jsoup;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;

    opens at.ac.fhcampuswien to javafx.fxml;
    exports at.ac.fhcampuswien;
}