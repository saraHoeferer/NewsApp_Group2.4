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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private boolean top = false;
    private boolean bit = false;

    //Exception
    //shows all news about bitcoin in textarea
    public void getNewsBitcoin(){
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
        bit = true;
        top = false;
    }

    //Exception
    //shows all news in textarea
    public void getTopNews() throws IOException, NewsApiExceptions {
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
        top = true;
        bit = false;
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
       response = null;
    }

    public void leaveApp(){
        Platform.exit();
    }

    //shows welcome text in textarea
    public void printHello(){
        text.setText("Willkommen auf der NewsApp der Gruppe 2/4\n\nCreators: Christiane Haider - Sophia Hölzl - Sara Höferer");
        response = null;
    }

    public void filterSource(){
        if (response == null) {
            text.setText("Sie müssen zuerst eine Seite auswählen um diese Abfrage durchführen zu können");
        } else {
            Stream<Article> streamofArticle = response.getArticles().stream();
            long cnt = streamofArticle
                    .filter( article -> article.getSource().getName().equals("Kurier.at"))
                    .count();
            text.setText("Derzeit haben wir " + cnt + " Kurier.at Artikel.");
        }
        paneText.setContent(text);
        paneText.setFitToHeight(true);

    }

    public void longestName(){
        if (response == null){
            text.setText("Sie müssen zuerst eine Seite auswählen um diese Abfrage durchführen zu können");
        } else {
            List<String> authors = new ArrayList<String>();
            for (int i = 0; i < response.getArticles().size(); i++) {
                authors.add(response.getArticles().get(i).getAuthor());
            }
            String mostCommon = authors.stream()
                    .max(Comparator.comparing(String::length)).get();
            text.setText("Der/Die Autor/in mit dem längsten Namen ist: ' " + mostCommon + " '.");
        }
    }

    public void shortestHeadline() throws IOException, NewsApiExceptions {
        if (top){
            response = ctrl.getTopHeadlinesAustria();
        } else if (bit){
            response = ctrl.getAllNewsBitcoin();
        }

        if (response == null){
            text.setText("Sie müssen zuerst eine Seite auswählen um diese Abfrage durchführen zu können");
        } else {
            StringJoiner joiner = new StringJoiner("\n\n");
            Stream<Article> streamFromList = response.getArticles().stream();
            List<Article> newList = streamFromList
                    .filter(article -> article.getTitle().length() < 15)
                    .collect(Collectors.toList());
            if (newList.size() == 0){
                text.setText("Es gibt leider keine Artikel die einen Titel mit weniger als 15 Zeichen haben.");
            } else {
                for (Article article : newList) {
                    joiner.add(article.toString());
                }
                text.setText(joiner.toString());
            }
        }
        paneText.setContent(text);
        paneText.setFitToHeight(true);
    }

    public void sortDescription() throws IOException, NewsApiExceptions {
        if (top){
            response = ctrl.getTopHeadlinesAustria();
        } else if (bit) {
            response = ctrl.getAllNewsBitcoin();
        }
        if (response == null) {
            text.setText("Sie müssen zuerst eine Seite auswählen um diese Abfrage durchführen zu können");
        } else {
            StringJoiner joiner = new StringJoiner("\n\n");
            List<Article> sorted = sortDescriptionStream(response);
            for (Article article : sorted) {
                joiner.add(article.toString());
            }
            text.setText(joiner.toString());
        }
        paneText.setContent(text);
        paneText.setFitToHeight(true);
    }

    private List<Article> sortDescriptionStream(NewsResponse response){
        Stream<Article> streamOfArticles = response.getArticles().stream();
        List<Article> sorted = streamOfArticles
                .sorted((a1, a2) -> {
                    if (a1.getDescription() == null){
                        return -1;
                    } else if (a2.getDescription() == null){
                        return 1;
                    } else {
                        if (a1.getDescription().length() == a2.getDescription().length())
                            return a1.getDescription().compareTo(a2.getDescription());
                        else if (a1.getDescription().length() > a2.getDescription().length())
                            return 1;
                        else return -1;
                    }
                })
                .collect(Collectors.toList());
        return sorted;
    }

    public void filterMostArticles(){
        if (response == null){
            text.setText("Sie müssen zuerst eine Seite auswählen um diese Abfrage durchführen zu können");
        } else {
            List<String> sources = new ArrayList<String>();
            for (int i = 0; i < response.getArticles().size(); i++) {
                sources.add(response.getArticles().get(i).getSource().getName());
            }

            String mostCommon = sources.stream()
                    .limit(20)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream().max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElse(null);
            text.setText("Die meisten Artikel stammen von ' " + mostCommon + " '.");
            paneText.setContent(text);
            paneText.setFitToHeight(true);
        }

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