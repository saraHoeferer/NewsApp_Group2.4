package at.ac.fhcampuswien;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    @DisplayName("Test for setting variable Articles")
    public void setArticles(){
        AppController ctrl = new AppController();
        List <Article> list = new ArrayList<Article>();
        list.add(new Article("Dummy", "test"));
        list.add(new Article("Dummy2", "test"));
        ctrl.setArticles(list);

        assertEquals(list.size(), ctrl.getArticleCount());

    }

    @Test
    @DisplayName("Test check countArticles with full list")
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
    @DisplayName("Test check countArticles with empty list")
    public void getArticleCountEmptyList(){
        AppController ctrl = new AppController();
        List <Article> list = new ArrayList<Article>();
        ctrl.setArticles(list);
        int cnt = ctrl.getArticleCount();
        assertEquals(0, cnt);
    }
}
