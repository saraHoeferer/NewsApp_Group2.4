module at.ac.fhcampuswien {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp;
    requires com.google.gson;

    opens at.ac.fhcampuswien to javafx.fxml;
    exports at.ac.fhcampuswien;
}