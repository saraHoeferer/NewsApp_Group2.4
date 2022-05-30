package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloController {

    public Button buttonCount;
    public Button buttonTop;
    public Button buttonBit;
    public Button buttonHome;
    public ImageView calcImage;
    public ImageView articleImage;
    public ImageView bitcoinImage;
    public ImageView homeImage;
    public ImageView exitImage;
    public Button buttonLeave;
    public ScrollPane paneText;
    public TextArea text;
    public Button buttonNYT;
    public Button buttonMost;
    public Button buttonLongest;
    public Button buttonLess;
    public Button buttonSort;
    public Label labelEndpoint;
    public Label labelQuery1;
    public TextField fieldQuery;
    public Label labelQuery2;
    public ComboBox <Category> boxCategory;
    public Label labelQuery3;
    public ComboBox<Country> boxCountry;
    public ComboBox <Language> boxLanguage;
    public ComboBox <SortBy> boxSortBy;
    public Button buttonSubmit;
    public Label labelDownload;
    public TextField fieldDownload;
    public Button buttonDownload;
    public GridPane boxGrid;



    private final AppController ctrl = new AppController();
    private NewsResponse response;
    private String message = "";

    private boolean top = false;
    private boolean bit = false;

    @FXML
    private void initialize() {
        defaultMenu();
        boxCountry.getItems().addAll(Country.values());
        boxCategory.getItems().addAll(Category.values());
        boxLanguage.getItems().addAll(Language.values());
        boxSortBy.getItems().addAll(SortBy.values());
    }

    //shows all news about bitcoin in textarea
    public void getNewsBitcoin() {
        bit = true;
        top = false;
        manageMenu();
        try {
            response = ctrl.getAllNewsBitcoin();

        } catch (NewsApiExceptions e) {
            message = e.getMessage();
            response = null;
        }
        printText(message);
        printArticle(response);
    }

    //shows all news in textarea
    public void getTopNews() {
        top = true;
        bit = false;
        manageMenu();
        try {
            response = ctrl.getTopHeadlinesAustria();
        } catch (NewsApiExceptions e) {
            message = e.getMessage();
            response = null;
        }
        printText(message);
        printArticle(response);
    }

    //shows amount of articles in textarea
    public void getArticleCount() {
        int number = ctrl.getArticleCount();
        if (bit) {
            message = "At the moment we offer " + number + " Bitcoin articles.";
        } else if (top){
            message = "At the moment we offer " + number + " Top-News articles.";
        }
        printText(message);
        response = null;
        bit = false;
        top = false;
        manageMenu();
    }

    public void leaveApp() {
        Platform.exit();
    }

    //shows welcome text in textarea
    public void printHello() {
        printText("Welcome to the NewsApp of Group 2/4\n\nCreators: Christiane Haider - Sophia Hölzl - Sara Höferer");
        response = null;
        bit = false;
        top = false;
        manageMenu();
    }

    public void printText(String message) {
        text.setText(message);
        paneText.setContent(text);
        paneText.setFitToHeight(true);
    }

    private void printArticle(NewsResponse response) {
        StringJoiner joiner = new StringJoiner("\n\n");
        if (response != null && response.getArticles() != null && response.getArticles().size() != 0) {
            int cnt = 1;
            for (Article article : response.getArticles()) {
                joiner.add(cnt + ". " + article.toString());
                cnt++;
            }
            message = joiner.toString();
        } else if (response != null && response.getStatus().equals("ok") && response.getArticles().size() == 0) {
            message = "There were no results with your query.";
        }
        printText(message);
    }

    public void buttonHandler(MouseEvent e) {
        if (response == null) {
            message = "You need to either chose Top-News or Bitcoin first before making a request.";
        } else {
            if (e.getSource().equals(buttonMost)) {
                filterMostArticles();
            } else if (e.getSource().equals(buttonNYT)) {
                filterSource("New York Times");
            } else if (e.getSource().equals(buttonLongest)) {
                longestName();
            } else if (e.getSource().equals(buttonLess)) {
                shortestHeadline();
            } else if (e.getSource().equals(buttonSort)) {
                sortDescription();
            }
        }
        printText(message);
    }

    private void filterSource(String source) {
        Stream<Article> streamofArticle = response.getArticles().stream();
        long cnt = streamofArticle
                .filter(article -> article.getSource().getName().equals(source))
                .count();
        message = "At the moment we offer " + cnt + " articles from " + source + ".";

    }

    private void longestName() {
        List<String> authors = new ArrayList<>();
        for (int i = 0; i < response.getArticles().size(); i++) {
            authors.add(response.getArticles().get(i).getAuthor());
        }
        String mostCommon = authors.stream()
                .max(Comparator.comparing(String::length)).get();
        message = "The author with the longest name is called: ' " + mostCommon + " '.";
    }

    private void shortestHeadline() {
        StringJoiner joiner = new StringJoiner("\n\n");
        Stream<Article> streamFromList = response.getArticles().stream();
        List<Article> newList = streamFromList
                .filter(article -> article.getTitle().length() < 60)
                .collect(Collectors.toList());
        if (newList.size() == 0) {
            message = "Unfortunately, there is no article that has less than 15 characters in its title.";
        } else {
            for (Article article : newList) {
                joiner.add(article.toString());
            }
            message = joiner.toString();
        }
    }

    private void sortDescription() {
        StringJoiner joiner = new StringJoiner("\n\n");
        Stream<Article> streamOfArticles = response.getArticles().stream();
        List<Article> sorted = streamOfArticles
                .sorted((a1, a2) -> {
                    if (a1.getDescription() == null) {
                        return -1;
                    } else if (a2.getDescription() == null) {
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
        for (Article article : sorted) {
            joiner.add(article.toString());
        }
        message = joiner.toString();
    }

    private void filterMostArticles() {
        List<String> sources = new ArrayList<>();
        for (int i = 0; i < response.getArticles().size(); i++) {
            sources.add(response.getArticles().get(i).getSource().getName());
        }

        String mostCommon = sources.stream()
                .limit(20)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(null);

        message = "Most of the Articles were published by ' " + mostCommon + " '.";
    }

    private void manageMenu() {
        if (top) {
            setMenuBackUp();
            setMenuTop();
        } else if (bit) {
            setMenuBackUp();
            setMenuBit();
        } else {
            defaultMenu();
        }
    }

    private void defaultMenu() {
        labelQuery1.setVisible(false);
        fieldQuery.setVisible(false);
        labelQuery2.setVisible(false);
        labelQuery3.setVisible(false);
        boxCategory.setVisible(false);
        boxCountry.setVisible(false);
        boxLanguage.setVisible(false);
        boxSortBy.setVisible(false);
        buttonSubmit.setVisible(false);
        labelEndpoint.setText("Click on Top-News or Bitcoin an see what happens.");
        labelDownload.setText("");
        buttonDownload.setVisible(false);
        fieldDownload.setVisible(false);
    }

    private void setMenuBackUp() {
        labelQuery1.setVisible(true);
        fieldQuery.setVisible(true);
        labelQuery2.setVisible(true);
        labelQuery3.setVisible(true);
        buttonSubmit.setVisible(true);
        labelDownload.setText("Choose an article: ");
        fieldDownload.setVisible(true);
        buttonDownload.setVisible(true);
    }

    private void setMenuTop() {
        boxCountry.setVisible(true);
        boxCategory.setVisible(true);
        boxSortBy.setVisible(false);
        boxLanguage.setVisible(false);

        boxCountry.setValue(Country.AUSTRIA);
        boxCategory.setValue(Category.NONE);
        labelEndpoint.setText("Top-Headlines");
        labelQuery1.setText("Query");
        labelQuery2.setText("Country");
        labelQuery3.setText("Category");
    }

    private void setMenuBit() {
        boxSortBy.setVisible(true);
        boxLanguage.setVisible(true);
        boxCountry.setVisible(false);
        boxCategory.setVisible(false);

        boxLanguage.setValue(Language.NONE);
        boxSortBy.setValue(SortBy.NONE);
        labelEndpoint.setText("Everything");
        labelQuery1.setText("Query");
        labelQuery2.setText("Language");
        labelQuery3.setText("Sort By");
    }

    public void getYourNews() {
        String query = "";
        if (fieldQuery.getText() != null) {
            query = fieldQuery.getText();
        }
        if (bit) {
            getYourNewsBit(query);
        } else if (top) {
            getYourNewsTop(query);
        } else {
            message = "Some Problem occurred. We're sorry!";
            printText(message);
        }
    }

    private void getYourNewsBit(String query) {
        try {
            response = ctrl.getYourNewsEverything(Endpoint.EVERYTHING, query, boxLanguage.getValue(), boxSortBy.getValue());
        } catch (NewsApiExceptions e) {
            message = e.getMessage();
            response = null;
        }
        printText(message);
        printArticle(response);
    }

    private void getYourNewsTop(String query) {
        try {
            response = ctrl.getYourNewsTop(Endpoint.TOPHEADLINES, query, boxCountry.getValue(), boxCategory.getValue());
        } catch (NewsApiExceptions e) {
            message = e.getMessage();
            response = null;
        }
        printText(message);
        printArticle(response);
    }

    public void doDownload() {
        String dirPath = new File("").getAbsolutePath();
        dirPath += "\\downloadedArticle.txt";
        if (fieldDownload.getText() != null && !fieldDownload.getText().equals("")){
            int input = Integer.parseInt(fieldDownload.getText());
            if (input - 1  < response.getArticles().size() && input > 0){
                try {
                    ctrl.downloadFile(response.getArticles().get(input - 1).getUrl(), dirPath);
                    message = "Your article was downloaded successfully!";
                } catch (NewsApiExceptions e){
                    message = e.getMessage();
                }
            } else {
                message = "Only articles form the list can be downloaded.";
            }
        } else {
            message = "You must choose an article in order to download it.";
        }
        printText(message);
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
        } else if (event.getSource().equals(buttonHome)) {
            buttonHome.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
            homeImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/homeDark.png"));
        } else if (event.getSource().equals(buttonLeave)) {
            buttonLeave.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black");
            exitImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/exitDark.png"));
        } else if (event.getSource().equals(buttonSort)) {
            buttonSort.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-border-radius: 10");
        } else if (event.getSource().equals(buttonLess)) {
            buttonLess.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-border-radius: 10");
        } else if (event.getSource().equals(buttonLongest)) {
            buttonLongest.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-border-radius: 10");
        } else if (event.getSource().equals(buttonNYT)) {
            buttonNYT.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-border-radius: 10");
        } else if (event.getSource().equals(buttonMost)) {
            buttonMost.setStyle("-fx-background-color: #ffffff; -fx-text-fill: black; -fx-border-radius: 10");
        }
    }

    //changes color back after mouse exits button
    public void changeColorBack(MouseEvent event) {
        if (event.getSource().equals(buttonCount)) {
            buttonCount.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            calcImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/calcLight.png"));
        } else if (event.getSource().equals(buttonTop)) {
            buttonTop.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            articleImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/articleLight.png"));
        } else if (event.getSource().equals(buttonBit)) {
            buttonBit.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            bitcoinImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/bitcoinLight.png"));
        } else if (event.getSource().equals(buttonHome)) {
            buttonHome.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            homeImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/homeLight.png"));
        } else if (event.getSource().equals(buttonLeave)) {
            buttonLeave.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff");
            exitImage.setImage(new Image("file:src/main/resources/at/ac/fhcampuswien/exitLight.png"));
        } else if (event.getSource().equals(buttonSort)) {
            buttonSort.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff; -fx-border-radius: 10");
        } else if (event.getSource().equals(buttonLess)) {
            buttonLess.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff; -fx-border-radius: 10");
        } else if (event.getSource().equals(buttonLongest)) {
            buttonLongest.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff; -fx-border-radius: 10");
        } else if (event.getSource().equals(buttonNYT)) {
            buttonNYT.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff; -fx-border-radius: 10");
        } else if (event.getSource().equals(buttonMost)) {
            buttonMost.setStyle("-fx-background-color: #56419c; -fx-border-color: #ffffff; -fx-border-radius: 10");
        }
    }
}