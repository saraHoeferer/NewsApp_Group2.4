package at.ac.fhcampuswien;

import java.util.ArrayList;
import java.util.List;

public class AppController {
    private List <Article> articles;

    public AppController(){
        articles = new ArrayList<Article>();
        articles = this.generateMockList();
    }

    public void setArticles (List <Article> articles){
        for (int i = 0; i < articles.size(); i++) {
            this.articles.add(articles.get(i));
        }
    }

    public int getArticleCount (){
        return 0;
    }

    public List <Article> getTopHeadlinesAustria (){
        return articles;
    }

    public List <Article> getAllNewsBitcoind () {
        return articles;
    }

    protected static List <Article> filterList(String query, List <Article> articles) {
        return articles;
    }

    private List <Article> generateMockList(){
        articles.add(new Article("Dummy", "test"));
        articles.add(new Article("Dummy2", "test"));
        articles.add(new Article("Dummy3", "test"));
        return articles;
    }

}
