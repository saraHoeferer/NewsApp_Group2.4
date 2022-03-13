package at.ac.fhcampuswien;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test
    public void getArticleCountFullList(){
        AppController ctrl = new AppController();

        List <Article> list = new ArrayList<Article>();
        list.add(new Article("Dummy", "test"));
        list.add(new Article("Dummy2", "test"));
        ctrl.setArticles(list);

        int cnt =  ctrl.getArticleCount();
        assertEquals(list.size(), cnt);
    }

    @Test
    public void getArticleCountEmptyList(){
        AppController ctrl = new AppController();
        int cnt = ctrl.getArticleCount();
        assertEquals(0, cnt);
    }

    @Test
    public void getAllNewsBitcoin(){ // when uppercase is used in the article
        AppController ctrl = new AppController(); // create new controller object
        List <Article> list = new ArrayList<Article>(); // create a new empty array list
        ctrl.setArticles(list); // set list as articles
        assertEquals(4, ctrl.getAllNewsBitcoin().size()); //compare size of list to actual value
    }
}
