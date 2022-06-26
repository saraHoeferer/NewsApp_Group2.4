package at.ac.fhcampuswien;

import at.ac.fhcampuswien.enumerations.*;
import at.ac.fhcampuswien.filterStructuralPattern.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.*;

public class HelloController {

    //FXML variables
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
    public Button buttonDownload;
    public GridPane boxGrid;

    //instance variables
    private final AppController ctrl = AppController.getInstance();
    private NewsResponse response;
    private String message = "";
    private boolean top = false;
    private boolean bit = false;

    //variables for Structural Patter "FILTER"
    private final Criteria sortDescription = new SortDescription();
    private final Criteria shortestHeadline = new ShortestHeadline();
    private final Criteria sourceFilter = new SourceFilter();
    private final Criteria longestName = new longestName();
    private final Criteria mostArticles = new mostArticles();

    //initialize App with values for ComboBoxes
    @FXML
    private void initialize() {
        //and hide Menu at the beginning
        defaultMenu();
        boxCountry.getItems().addAll(Country.values());
        boxCategory.getItems().addAll(Category.values());
        boxLanguage.getItems().addAll(Language.values());
        boxSortBy.getItems().addAll(SortBy.values());
    }

    //function to handle different button events
    //Use of Structural Pattern FILTER
    //due the fact that all the filter of the Articles list happens in different filter classes
    //code in hello controller is way less than before, so now it all happens in button handler class on not in own functions
    public void buttonHandler(MouseEvent e) {
        boolean printArticle = false;
        //if response is null
        if (response == null) {
            //You can't do any request so this message will be seen
            message = "You need to either chose Top-News or Bitcoin first before making a request.";
            //else define which button is pressed
        } else {
            //choose function according to which button was pushed
            //only thing all of those events do is get List related to whatever they need to filter and then present result to user
            if (e.getSource().equals(buttonMost)) {
                //Which Source has the most articles
                //get filtered list
                List<Article> mostCommon = mostArticles.criteria(response.getArticles());
                //set message
                message = "Most of the Articles were published by ' " + mostCommon.get(0).getSource().getName() + " '.";
            } else if (e.getSource().equals(buttonNYT)) {
                //How many articles are from NYT
                //get filtered list
                List<Article> source = sourceFilter.criteria(response.getArticles());
                //set message
                message = "At the moment we offer " + source.size() + " articles from the New York Times.";
            } else if (e.getSource().equals(buttonLongest)) {
                //which author has longest name
                //get filtered list
                List<Article> name = longestName.criteria(response.getArticles());
                //set message
                message = "The author with the longest name is called: ' " + name.get(0).getAuthor() + " '.";
            } else if (e.getSource().equals(buttonLess)) {
                //Headlines with characters less than 60
                //get filtered list
                List<Article> shortest = shortestHeadline.criteria(response.getArticles());
                //if none were found
                if (shortest.size() == 0) {
                    //message is this
                    message = "Unfortunately, there is no article that has less than 15 characters in its title.";
                } else {
                    //if articles were found print them
                    printArticle = true;
                    printArticle(shortest);
                }
            } else if (e.getSource().equals(buttonSort)) {
                //Sort by length of description
                //get filtered list
                List<Article> sorted = sortDescription.criteria(response.getArticles());
                //print articles
                printArticle = true;
                printArticle(sorted);
            }
        }
        //print whatever message is set if a list of articles is not already printed
        if (!printArticle) {
            printText(message);
        }
    }

    //Method from Solution of Exercise 3 from Leon's Github
    public void downloadURLs(){

        //try to do download
        try {
            //measure time before function call
            long startTimeSeq = System.currentTimeMillis();
            //make sequential download
            int resultSequential = ctrl.downloadURLs(new SequentialDownloader());
            //measure time after function call
            long entTimeSeq = System.currentTimeMillis();
            //set message -> time is difference between end and start + print how many urls were downloaded
            message = "Time it took Sequential Downloader in ms was " + (entTimeSeq-startTimeSeq) + ". " + resultSequential + " URLS were downloaded\n";

            //measure time before function call
            long startTimePar = System.currentTimeMillis();
            //make parallel download
            int resultParallel = ctrl.downloadURLs(new ParallelDownloader());
            //measure time after function call
            long entTimePar = System.currentTimeMillis();
            //extend message -> time is difference between end and start + print how many urls were downloaded
            message += "Time it took Parallel Downloader in ms was " + (entTimePar-startTimePar) + ". " + resultParallel + " URLS were downloaded\n";

            //print message
            printText(message);
        } catch (NewsApiExceptions e){
            //else catch exception and print it to user
            printText(e.getMessage());
        }
    }

    //shows all news about bitcoin in textarea
    public void getNewsBitcoin() {
        //set Page to Bitcoin
        bit = true;
        top = false;
        //show Menu
        manageMenu();
        //try to get response
        try {
            response = ctrl.getAllNewsBitcoin();
        //or catch exception and show message to user
        } catch (NewsApiExceptions e) {
            message = e.getMessage();
            //response gets null
            response = null;
        }
        //print text and response
        printText(message);
        //if response is not null
        if (response != null) {
            //print article list
            printArticle(response.getArticles());
        }
    }

    //shows all news in textarea
    public void getTopNews() {
        //set Page to TopHeadlines
        top = true;
        bit = false;
        //show Menu
        manageMenu();
        //try to get response
        try {
            response = ctrl.getTopHeadlinesAustria();
        //or catch exception and show message to user
        } catch (NewsApiExceptions e) {
            message = e.getMessage();
            //set response null
            response = null;
        }
        //print text and response
        printText(message);
        //if response is not null
        if (response != null) {
            //print article list
            printArticle(response.getArticles());
        }
    }

    //shows amount of articles in textarea
    public void getArticleCount() {
        //get article count
        int number = ctrl.getArticleCount();
        //if its set on bit
        if (bit) {
            //show this message
            message = "At the moment we offer " + number + " Bitcoin articles.";
        //if its set on top
        } else if (top){
            //show this message
            message = "At the moment we offer " + number + " Top-News articles.";
        }
        //print message
        printText(message);
        //set response null
        response = null;
        //set bit/top false
        bit = false;
        top = false;
        //set hide Menu
        manageMenu();
    }

    //function to leave app
    public void leaveApp() {
        Platform.exit();
    }

    //shows welcome text in textarea
    public void printHello() {
        printText("Welcome to the NewsApp of Group 2/4\n\nCreators: Christiane Haider - Sophia Hölzl - Sara Höferer");
        //set response null
        response = null;
        //set bit/top false
        bit = false;
        top = false;
        //hide menu
        manageMenu();
    }

    //function to print message to App
    public void printText(String message) {
        //set Textfield to message
        text.setText(message);
        //manage content display
        paneText.setContent(text);
        paneText.setFitToHeight(true);
    }

    //function to print response to App
    private void printArticle(List articles) {
        //create new String joiner
        StringJoiner joiner = new StringJoiner("\n\n");
        //if response was successful
        if (articles != null && articles.size() != 0) {
            //add articles + number to StringJoiner
            for (int i = 0; i < articles.size(); i++){
                joiner.add(i+1 + ". " + articles.get(i).toString());
            }
            //message is StringJoiner
            message = joiner.toString();
        //if response was successful but there weren't any results
        } else if (articles != null) {
            //print this message
            message = "There were no results with your query.";
        }
        //print message
        printText(message);
    }

    //function that manage the hide/show of the sidemenu
    private void manageMenu() {
        //if topHeadlines
        if (top) {
            //make menu visible
            setMenuBackUp();
            //make buttons for menu top visible and set labels
            setMenuTop();
        //if Bitcoin
        } else if (bit) {
            //make menu visible
            setMenuBackUp();
            //make button for menu bit visible and set labels
            setMenuBit();
        //else set default menu (nothing but one label is visible)
        } else {
            defaultMenu();
        }
    }

    //make everything invisible besides endpoint label
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
    }

    //make all buttons visible again besides combobox items
    private void setMenuBackUp() {
        labelQuery1.setVisible(true);
        fieldQuery.setVisible(true);
        labelQuery2.setVisible(true);
        labelQuery3.setVisible(true);
        buttonSubmit.setVisible(true);
        labelDownload.setText("Download all articles");
        buttonDownload.setVisible(true);
    }

    //set menu for TopHeadlines
    private void setMenuTop() {
        //set combobox for Country and Category visible
        boxCountry.setVisible(true);
        boxCategory.setVisible(true);
        //set combobox for Language and SortBy invisible
        boxSortBy.setVisible(false);
        boxLanguage.setVisible(false);

        //set default values for comboboxes
        boxCountry.setValue(Country.AUSTRIA);
        boxCategory.setValue(Category.NONE);
        //set texts for lables
        labelEndpoint.setText("Top-Headlines");
        labelQuery1.setText("Query");
        labelQuery2.setText("Country");
        labelQuery3.setText("Category");
    }

    //set menu for Bitcoin
    private void setMenuBit() {
        //set combobox for Language and SortBy visible
        boxSortBy.setVisible(true);
        boxLanguage.setVisible(true);
        //set combobox for Country and Category invisible
        boxCountry.setVisible(false);
        boxCategory.setVisible(false);

        //set default values for comboboxes
        boxLanguage.setValue(Language.NONE);
        boxSortBy.setValue(SortBy.NONE);
        //set texts for lables
        labelEndpoint.setText("Everything");
        labelQuery1.setText("Query");
        labelQuery2.setText("Language");
        labelQuery3.setText("Sort By");
    }

    //function to manage specific request recording to endpoint
    public void getYourNews() {
        //get value for query
        String query = "";
        if (fieldQuery.getText() != null) {
            query = fieldQuery.getText();
        }
        //if endpoint bitcoin
        if (bit) {
            //make bitcoin request
            getYourNewsBit(query);
        //if endpoint is topheadlines
        } else if (top) {
            //make topheadlines request
            getYourNewsTop(query);
        //if user landed somewhere else
        } else {
            //print this message
            message = "Some Problem occurred. We're sorry!";
            printText(message);
        }
    }

    //function to make specific request for endpoint bitcoin
    private void getYourNewsBit(String query) {
        //try to get response
        try {
            //make function call with specific parameters
            response = ctrl.getYourNewsEverything(Endpoint.EVERYTHING, query, boxLanguage.getValue(), boxSortBy.getValue());
        //catch and print Exception
        } catch (NewsApiExceptions e) {
            message = e.getMessage();
            //set response null
            response = null;
        }
        //print message
        printText(message);
        //print articles
        if (response != null) {
            printArticle(response.getArticles());
        }
    }
    //function to make specific request for endpoint topheadlines
    private void getYourNewsTop(String query) {
        //try to get response
        try {
            //make function call with specific parameters
            response = ctrl.getYourNewsTop(Endpoint.TOPHEADLINES, query, boxCountry.getValue(), boxCategory.getValue());
            //catch and print Exception
        } catch (NewsApiExceptions e) {
            message = e.getMessage();
            //set response nul
            response = null;
        }
        //print message
        printText(message);
        //print articles
        if (response != null) {
            printArticle(response.getArticles());
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