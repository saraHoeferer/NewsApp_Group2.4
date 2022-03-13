package at.ac.fhcampuswien;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppController {
    private List <Article> articles;

    public AppController(){
        articles = new ArrayList<Article>();
        articles = this.generateMockList();
    }

    public void setArticles (List <Article> articles){ this.articles = articles; }

    public int getArticleCount (){
        if (articles == null){
            return 0;
        } else {
            return articles.size();
        }
    }

    public List <Article> getTopHeadlinesAustria (){
        return articles;
    }

    public List <Article> getAllNewsBitcoin () {
        List<Article> list = new ArrayList();
        return list;
    }

    protected static List<Article> filterList(String query, List <Article> articles) {
        List<Article> filteredList = new ArrayList();
        return filteredList;
    }

    private List <Article> generateMockList(){
        articles.add(new Article("Dummy", "test"));
        articles.add(new Article("Dummy2", "test"));
        articles.add(new Article("Dummy3", "test"));
        articles.add(new Article("Elon Musk", "Bitcoins are the currency of the future"));
        articles.add(new Article("Eminem", "Rap God"));
        articles.add(new Article("Jeff Bezos", "bItCoInS in the rise"));
        articles.add(new Article("Whitesnake", "Here we go again"));
        articles.add(new Article("Mark Zuckerberg", "BITCOIN"));
        articles.add(new Article("HAHA", "bitcoins ftw"));
        return articles;
    }

}
