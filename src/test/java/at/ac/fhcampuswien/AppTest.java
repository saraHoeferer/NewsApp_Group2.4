package at.ac.fhcampuswien;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest extends AppController{
    private AppController ctrl;
    private List<Article> expected;
    private List<Article> actual;

    @BeforeEach
    public void setUp(){
        ctrl = new AppController();
        expected = new ArrayList<>();
        actual = new ArrayList<>();
    }

    @Test
    @DisplayName("SetArticles with Expected List")
    public void setArticles(){
        try {
            //Add articles to expected List
            expected.add(new Article("Hayley Maguire", "How Austrian states are preparing for Ukrainian refugees"));
            expected.add(new Article("Hayley Maguire", "'The pandemic has not been mastered': Vienna to tighten Covid measures"));
            expected.add(new Article("Ellie Harrison", "Arnold Schwarzenegger speaks candidly about Nazi father as he makes plea to Russia amid Ukraine invasion"));
            expected.add(new Article("Amit Mudgill", "Top cryptocurrency prices today: Bitcoin Ethereum, BNB recover after up to 8% plunge"));
            expected.add(new Article("Phil Blanche", "Scotland-Ukrainer postponed until June but Wales semi-final goes ahead as planned"));
            expected.add(new Article("NYT News Service", "BiTCoin was made for this moment. So why isn't it booming?"));
            expected.add(new Article("Reuters", "BITCOIN rallies after Biden signs executive order on digital assets"));
            expected.add(new Article("Henry Saker-Clark", "Omicron cuts into sales momentum at Zara owner Inditex"));
            expected.add(new Article("Medha Singh", "Cryptoverse: bitcoin's scared of commitment, Mr Biden"));
            expected.add(new Article("AP news wire", "Shiffrin 5th after Olympic combined downhill; has medal shot"));

            //Set expected as Articles in AppController
            ctrl.setArticles(expected);

            //get Articles von AppController
            actual = ctrl.getArticles();

            //assert if expected List and actual List are identical
            assertEquals(expected.containsAll(actual), actual.containsAll(expected));
        } catch (Exception e){
            //else print Stacktrace
            e.printStackTrace();
            //And Test will fail
            fail();
        }

    }

    @Test
    @DisplayName("CountArticles with full list")
    public void getArticleCountFullList(){
        try {
            //Add articles to expected List
            expected.add(new Article("Hayley Maguire", "How Austrian states are preparing for Ukrainian refugees"));
            expected.add(new Article("Hayley Maguire", "'The pandemic has not been mastered': Vienna to tighten Covid measures"));
            expected.add(new Article("Ellie Harrison", "Arnold Schwarzenegger speaks candidly about Nazi father as he makes plea to Russia amid Ukraine invasion"));
            expected.add(new Article("Amit Mudgill", "Top cryptocurrency prices today: Bitcoin Ethereum, BNB recover after up to 8% plunge"));

            //Set expected as Articles in AppController
            ctrl.setArticles(expected);

            //Get Article count from AppController
            int cnt = ctrl.getArticleCount();

            //check if count and size are identical
            assertEquals(expected.size(), cnt);
        } catch (Exception e){
            //else print Stacktrace and fail
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("CountArticles with empty list")
    public void getArticleCountEmptyList(){
       try {
           //set empty expected List as articles
           ctrl.setArticles(expected);

           //Get Article count from AppController
           int cnt = ctrl.getArticleCount();

           //check if count equals 0 because of empty List
           assertEquals(0, cnt);
       } catch (Exception e) {
           //else print Stacktrace and fail
           e.printStackTrace();
           fail();
       }
    }

    @Test
    @DisplayName("Check All News Bitcoin")
    public void getAllNewsBitcoinTest(){
        try {
            //Add articles to expected List
            expected.add(new Article("Amit Mudgill", "Top cryptocurrency prices today: Bitcoin Ethereum, BNB recover after up to 8% plunge"));
            expected.add(new Article("NYT News Service", "BiTCoin was made for this moment. So why isn't it booming?"));
            expected.add(new Article("Reuters", "BITCOIN rallies after Biden signs executive order on digital assets"));
            expected.add(new Article("Medha Singh", "Cryptoverse: bitcoin's scared of commitment, Mr Biden"));

            //Get All News about Bitcoin from AppController
            actual = ctrl.getAllNewsBitcoin();

            //check if both lists are identical
            assertEquals(expected.containsAll(actual), actual.containsAll(expected));
        } catch (Exception e){
            //else print Stacktrace and fail
            e.printStackTrace();
            fail();//compare size of list to actual value
        }
    }

    @Test
    @DisplayName("Check Top Headlines Austria")
    public void getTopHeadlinesAustriaTest(){
        try {
            //Add articles to expected List
            expected.add(new Article("Hayley Maguire", "How Austrian states are preparing for Ukrainian refugees"));
            expected.add(new Article("Hayley Maguire", "'The pandemic has not been mastered': Vienna to tighten Covid measures"));
            expected.add(new Article("Ellie Harrison", "Arnold Schwarzenegger speaks candidly about Nazi father as he makes plea to Russia amid Ukraine invasion"));
            expected.add(new Article("Amit Mudgill", "Top cryptocurrency prices today: Bitcoin Ethereum, BNB recover after up to 8% plunge"));
            expected.add(new Article("Phil Blanche", "Scotland-Ukrainer postponed until June but Wales semi-final goes ahead as planned"));
            expected.add(new Article("NYT News Service", "BiTCoin was made for this moment. So why isn't it booming?"));
            expected.add(new Article("Reuters", "BITCOIN rallies after Biden signs executive order on digital assets"));
            expected.add(new Article("Henry Saker-Clark", "Omicron cuts into sales momentum at Zara owner Inditex"));
            expected.add(new Article("Medha Singh", "Cryptoverse: bitcoin's scared of commitment, Mr Biden"));
            expected.add(new Article("AP news wire", "Shiffrin 5th after Olympic combined downhill; has medal shot"));

            //Get All Top News from AppController
            actual = ctrl.getTopHeadlinesAustria();

            //check if both lists are identical
            assertEquals(expected.containsAll(actual), actual.containsAll(expected));
        } catch (Exception e){
            //else print Stacktrace and fail
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("Check filter List with query 'Ukrai'")
    public void filterListUkrai(){
        try {
            //Add articles to expected List
            expected.add(new Article("Hayley Maguire", "How Austrian states are preparing for Ukrainian refugees"));
            expected.add(new Article("Ellie Harrison", "Arnold Schwarzenegger speaks candidly about Nazi father as he makes plea to Russia amid Ukraine invasion"));
            expected.add(new Article("Phil Blanche", "Scotland-Ukrainer postponed until June but Wales semi-final goes ahead as planned"));

            //Get filtered List about 'ukrai' from AppController
            actual = filterList("ukrai", ctrl.getArticles());

            //check if both lists are identical
            assertEquals(expected.containsAll(actual), actual.containsAll(expected));
        } catch (Exception e){
            //else print Stacktrace and fail
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @DisplayName("Check filter List with query 'cr'")
    public void filterListCr(){
        try {
            //Add articles to expected List
            expected.add(new Article("Henry Saker-Clark", "Omicron cuts into sales momentum at Zara owner Inditex"));
            expected.add(new Article("Medha Singh", "Cryptoverse: bitcoin's scared of commitment, Mr Biden"));
            expected.add(new Article("Amit Mudgill", "Top cryptocurrency prices today: Bitcoin Ethereum, BNB recover after up to 8% plunge"));

            //Get filtered List about 'cr' from AppController
            actual = filterList("cr", ctrl.getArticles());

            //check if both lists are identical
            assertEquals(expected.containsAll(actual), actual.containsAll(expected));
        } catch (Exception e){
            //else print Stacktrace and fail
            e.printStackTrace();
            fail();
        }
    }
}
