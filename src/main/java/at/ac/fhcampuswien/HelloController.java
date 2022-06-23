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
    public TextField fieldDownload;
    public Button buttonDownload;
    public GridPane boxGrid;

    //instance variables
    private final AppController ctrl = new AppController();
    private NewsResponse response;
    private String message = "";
    private boolean top = false;
    private boolean bit = false;
    public URLBuilder build = new URLBuilder();

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

    // Method is needed for exercise 4 - ignore for exercise 3 solution
    private void downloadURLs(){
        try {
            int resultSequential = ctrl.downloadURLs(new SequentialDownloader());
            // TODO print time in ms it took to download URLs sequentially

            // TODO implement the process() function in ParallelDownloader class
            int resultParallel = ctrl.downloadURLs(new ParallelDownloader());

            // TODO print time in ms it took to download URLs parallel

        } catch (NewsApiExceptions e){
            System.out.println(e.getMessage());
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

    //function to handle different button events
    public void buttonHandler(MouseEvent e) {
        //if response is null
        if (response == null) {
            //You can't do any request so this message will be seen
            message = "You need to either chose Top-News or Bitcoin first before making a request.";
        //else define which button is pressed
        } else {
            //choose function according to which button was pushed
            if (e.getSource().equals(buttonMost)) {
                //Which Source has the most articles
                filterMostArticles();
            } else if (e.getSource().equals(buttonNYT)) {
                //How many articles are from NYT
                filterSource("New York Times");
            } else if (e.getSource().equals(buttonLongest)) {
                //which author has longest name
                longestName();
            } else if (e.getSource().equals(buttonLess)) {
                //Headlines with characters less than 15
                shortestHeadline();
            } else if (e.getSource().equals(buttonSort)) {
                //Sort by length of description
                sortDescription();
            }
        }
        //print whatever message is set
        printText(message);
    }

    //function which shows how many articles were published by specific source
    private void filterSource(String source) {
        //create Stream from articlelist
        Stream<Article> streamofArticle = response.getArticles().stream();
        //filter articles according to source and count them
        long cnt = streamofArticle
                .filter(article -> article.getSource().getName().equals(source))
                .count();
        //set message to this
        message = "At the moment we offer " + cnt + " articles from " + source + ".";

    }

    //function which shows which author has the longest name
    private void longestName() {
        List<String> authors = new ArrayList<>();
        //get authors of article list and put them in new list
        for (int i = 0; i < response.getArticles().size(); i++) {
            authors.add(response.getArticles().get(i).getAuthor());
        }
        //stream author list
        String mostCommon = authors.stream()
                //compare author name length and save the one with the most characters
                .max(Comparator.comparing(String::length)).get();
        //set message to this
        message = "The author with the longest name is called: ' " + mostCommon + " '.";
    }

    //function that shows articles with headlines less than specific amount of characters
    private void shortestHeadline() {
        //make new Stream from articleList
        Stream<Article> streamFromList = response.getArticles().stream();
        //filter for all articles which titel have less than 15 characters
        List<Article> newList = streamFromList
                .filter(article -> article.getTitle().length() < 15)
                //collect all them
                .collect(Collectors.toList());
        //if none were found
        if (newList.size() == 0) {
            //message is this
            message = "Unfortunately, there is no article that has less than 15 characters in its title.";
        //if articles were found print them
        } else {
            printArticle(newList);
        }
    }

    //function that shows articles sorted after the length of their description
    private void sortDescription() {
        //make stream of article list
        Stream<Article> streamOfArticles = response.getArticles().stream();
        //sort them
        List<Article> sorted = streamOfArticles
                .sorted((a1, a2) -> {
                    //if first description is null
                    if (a1.getDescription() == null) {
                        //second one is bigger
                        return -1;
                    //if second description is null
                    } else if (a2.getDescription() == null) {
                        //first one is bigger
                        return 1;
                    //if none of them are null
                    } else {
                        //if they have the same length
                        if (a1.getDescription().length() == a2.getDescription().length())
                            //compare alphabetically
                            return a1.getDescription().compareTo(a2.getDescription());
                        //if first one is bigger
                        else if (a1.getDescription().length() > a2.getDescription().length())
                            return 1;
                        //else second one is bigger
                        else return -1;
                    }
                })
                //collect them all after sorting
                .collect(Collectors.toList());
        //print them
        printArticle(sorted);
    }

    //function that shows which Sources published the most articles
    private void filterMostArticles() {
        //make new list
        List<String> sources = new ArrayList<>();
        //populate list with all sources from article list
        for (int i = 0; i < response.getArticles().size(); i++) {
            sources.add(response.getArticles().get(i).getSource().getName());
        }

        //stream source list
        String mostCommon = sources.stream()
                //collect occurence of all sources
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                //compare the entries and select the one with the most occurence
                .entrySet().stream().max(Map.Entry.comparingByValue())
                //make it values of string
                .map(Map.Entry::getKey).orElse(null);
        //print this
        message = "Most of the Articles were published by ' " + mostCommon + " '.";
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
        fieldDownload.setVisible(false);
    }

    //make all buttons visible again besides combobox items
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

    //function to perform download
    public void doDownload() {
        //get absolute path of project folder
        String dirPath = new File("").getAbsolutePath();
        //add textfile to it
        dirPath += "\\downloadedArticle.txt";
        //if input from user was made
        if (fieldDownload.getText() != null && !fieldDownload.getText().equals("")){
            //parse it to an integer
            int input = Integer.parseInt(fieldDownload.getText());
            //check if number is valid
            if (input - 1  < response.getArticles().size() && input > 0){
                //try to download the file
                try {
                    ctrl.downloadFile(response.getArticles().get(input - 1).getUrl(), dirPath);
                    //if successful print this
                    message = "Your article was downloaded successfully!";
                //catch exception and print message
                } catch (NewsApiExceptions e){
                    message = e.getMessage();
                }
            //if number is not valid
            } else {
                //print this
                message = "Only articles form the list can be downloaded.";
            }
        //if no input was made
        } else {
            //print this
            message = "You must choose an article in order to download it.";
        }
        //print message
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