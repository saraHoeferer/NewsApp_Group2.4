package at.ac.fhcampuswien;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class HelloController {

    @FXML
    private Button buttonCount;

    @FXML
    private Button buttonTop;

    @FXML
    private Button buttonBit;

    @FXML
    private Button buttonHome;

    @FXML
    private ImageView calcImage;

    @FXML
    private ImageView articleImage;

    @FXML
    private ImageView bitcoinImage;

    @FXML
    private ImageView homeImage;

    @FXML
    private ImageView exitImage;

    @FXML
    private Button buttonLeave;

    @FXML
    private ScrollPane paneText;

    @FXML
    private TextArea text;

    private final AppController ctrl = new AppController();
    private List <Article> articles = new ArrayList<Article>();
    private NewsResponse response;

    //Exception
    //shows all news about bitcoin in textarea
    public void getNewsBitcoin(){
        response = null;
        try {
            response = ctrl.getAllNewsBitcoin();
        } catch (UnknownHostException e){
            text.setText("Überprüfe deine Internetverbindung.");
        } catch (NewsApiExceptions e){
            text.setText(e.getMessage());
        } catch (IOException e){
            text.setText("da ist etwas schiefgegangen, da gab es einen IO Exception Fehler: " + e.getMessage());
        } catch (Exception e){
            text.setText("da ist etwas schiefgegangen, da gab es einen Fehler: " + e.getMessage());
        }
        printArticle(response);
    }

    //Exception
    //shows all news in textarea
    public void getTopNews(){
        response = null;
        try {
            response = ctrl.getTopHeadlinesAustria();
        } catch (UnknownHostException e){
            text.setText("Überprüfe deine Internetverbindung.");
        } catch (NewsApiExceptions e){
            text.setText(e.getMessage());
        } catch (IOException e){
            text.setText("da ist etwas schiefgegangen, da gab es einen IO Exception Fehler: " + e.getMessage());
        } catch (Exception e){
            text.setText("da ist etwas schiefgegangen, da gab es einen Fehler: " + e.getMessage());
        }
        printArticle(response);
    }

    private void printArticle (NewsResponse response){
        StringJoiner joiner = new StringJoiner("\n\n");
        if (response != null &&response.getArticles() != null && response.getArticles().size() != 0) {
            for (Article article : response.getArticles()) {
                joiner.add(article.toString());
            }
            text.setText(joiner.toString());
        } else if (response != null && response.getStatus().equals("ok") && response.getArticles().size() == 0){
            text.setText("Mit deiner Abfrage gab es leider keine Ergebnisse");
        }
        paneText.setContent(text);
        paneText.setFitToHeight(true);
    }

    //Exception
    //shows amount of articles in textarea
    public void getArticleCount(){
        int number;
        try {
            number = ctrl.getArticleCount();
            text.setText("Derzeit haben wir " + number + " Artikel auf unsere NewsApp.");
        } catch (UnknownHostException e){
            text.setText("Überprüfe deine Internetverbindung.");
        } catch (NewsApiExceptions e){
            text.setText(e.getMessage());
        } catch (IOException e){
            text.setText("da ist etwas schiefgegangen, da gab es einen IO Exception Fehler: " + e.getMessage());
        } catch (Exception e){
            text.setText("da ist etwas schiefgegangen, da gab es einen Fehler: " + e.getMessage());
        }
    }

    public void leaveApp(){
        Platform.exit();
    }

    //shows welcome text in textarea
    public void printHello(){
        text.setText("Willkommen auf der NewsApp der Gruppe 2/4\n\nCreators: Christiane Haider - Sophia Hölzl - Sara Höferer");
    }

    //change color when mouse hovers over button
    public void changeColor(MouseEvent event) {
        if (event.getSource().equals(buttonCount)) {
            buttonCount.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
            calcImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/calcDark.png"));
        } else if (event.getSource().equals(buttonTop)) {
            buttonTop.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
            articleImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/articleDark.png"));
        } else if (event.getSource().equals(buttonBit)) {
            buttonBit.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
            bitcoinImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/bitcoinDark.png"));
        } else if (event.getSource().equals(buttonHome)){
            buttonHome.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
            homeImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/homeDark.png"));
        } else if (event.getSource().equals(buttonLeave)){
            buttonLeave.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
            exitImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/exitDark.png"));
        }
    }

    //changes color back after mouse exits button
    public void changeColorBack(MouseEvent event){
        if (event.getSource().equals(buttonCount)) {
            buttonCount.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            calcImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/calcLight.png"));
        } else if (event.getSource().equals(buttonTop)) {
            buttonTop.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            articleImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/articleLight.png"));
        } else if (event.getSource().equals(buttonBit)) {
            buttonBit.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            bitcoinImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/bitcoinLight.png"));
        } else if (event.getSource().equals(buttonHome)){
            buttonHome.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            homeImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/homeLight.png"));
        } else if (event.getSource().equals(buttonLeave)){
            buttonLeave.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            exitImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/exitLight.png"));
        }
    }
}