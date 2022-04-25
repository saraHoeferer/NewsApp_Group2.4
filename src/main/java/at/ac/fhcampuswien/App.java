package at.ac.fhcampuswien;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);// 889 554
        stage.getIcons().add(new Image("file:src/main/resources/at/ac/fhcampuswien/icon1.PNG"));
        stage.setTitle("NewsApp Gruppe 2/4");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        //launch JavaFX Application
        launch();

        //launch Console Application
        //Menu mu = new Menu();
        //mu.start();
        //Platform.exit();
    }
}