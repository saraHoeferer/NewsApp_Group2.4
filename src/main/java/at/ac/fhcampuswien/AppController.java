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

    public List <Article> getAllNewsBitcoin () {
        return filterList("bitcoin", articles);
    }

    protected static List<Article> filterList(String query, List <Article> articles) { // takes String for requested phrase and list of articles
        List<Article> filteredList = new ArrayList(); // create new empty array list
        for(Article article: articles){ // go through all articles with for each loop
            if(article.getTitle().toLowerCase().contains(query)){ // cast title of article to lower case and look up if it contains the query
                filteredList.add(article); // if title contains query add to the new array list
            }
        }
        return filteredList; // return the filtered list
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
