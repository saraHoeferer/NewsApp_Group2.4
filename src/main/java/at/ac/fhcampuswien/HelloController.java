package at.ac.fhcampuswien;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;

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
    private Label labelText;

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

    private AppController ctrl = new AppController();
    private List <Article> articles = new ArrayList<Article>();

    public void getNewsBitcoin(){
        StringJoiner joiner = new StringJoiner("\n\n");
        articles = ctrl.getAllNewsBitcoin();
        for (Article article: articles) {
            joiner.add(article.toString());
        }
        text.setText(joiner.toString());
    }

    public void getTopNews(){
        StringJoiner joiner = new StringJoiner("\n\n");
        articles = ctrl.getTopHeadlinesAustria();
        for (Article article: articles) {
            joiner.add(article.toString());
        }
        text.setText(joiner.toString());
        paneText.setContent(text);
        paneText.setFitToHeight(true);

    }

    public void getArticleCount(){
        int number = 0;
        number = ctrl.getArticleCount();
        text.setText("Derzeit haben wir " + number + " Artikel auf unserer NewsApp");
    }

    public void leaveApp(){
        Platform.exit();
    }

    public void printHello(){
        text.setText("Willkommen auf der NewsApp der Gruppe 2/4\n\nCreators: Christiane Haider - Sophia Hölzl - Sara Höferer");
    }

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