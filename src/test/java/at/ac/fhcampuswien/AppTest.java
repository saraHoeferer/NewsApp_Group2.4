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
    public void getTopHeadlinesAustriaTest(){
        AppController ctrl = new AppController();
        List <Article> top; // = new ArrayList<Article>(); //neue leere Liste erstellt

        top = ctrl.getTopHeadlinesAustria(); //leere Liste mit der Methode getTopHeadlinesAustria befüllen

        assertEquals(3, top.size()); //Top 3 Schlagzeilen sollen ausgeführt werden
    }
}
