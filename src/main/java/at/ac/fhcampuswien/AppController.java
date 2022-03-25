package at.ac.fhcampuswien;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppController {
    private List <Article> articles;


    public AppController(){
        articles = new ArrayList<>();
        articles = this.generateMockList();
    }

    public void setArticles (List <Article> articles){ this.articles = articles; }

    public int getArticleCount () {
        if (articles == null){
            return 0;
        } else {
            return articles.size();
        }
    }
    public List <Article> getArticles(){
        return articles;
    }

    public List <Article> getTopHeadlinesAustria (){
        /*List<Article> topHeadLines = new ArrayList<>();

        for (int i = 0; i < 3; i++) { //die ersten 3 Schlagzeilen
            topHeadLines.add(articles.get(i)); //leere Liste topHeadLines mit den ersten 3 Artikel der article Liste befüllen
        }
        */
        return articles;
    }

    public List <Article> getAllNewsBitcoin () {
        return filterList("bitcoin", articles);
    }

    protected static List<Article> filterList(String query, List <Article> articles) { // takes String for requested phrase and list of articles
        List <Article> filteredList = new ArrayList(); // create new empty array list
        for(Article article: articles){ // go through all articles with for each loop
            if(article.getTitle().toLowerCase().contains(query)){ // cast title of article to lower case and look up if it contains the query
                filteredList.add(article); // if title contains query add to the new array list
            }
        }
        return filteredList; // return the filtered list
    }

    private List <Article> generateMockList(){
        articles.add(new Article("Hayley Maguire", "How Austrian states are preparing for Ukrainian refugees"));
        articles.add(new Article("Hayley Maguire", "'The pandemic has not been mastered': Vienna to tighten Covid measures"));
        articles.add(new Article("Ellie Harrison", "Arnold Schwarzenegger speaks candidly about Nazi father as he makes plea to Russia amid Ukraine invasion"));
        articles.add(new Article("Amit Mudgill", "Top cryptocurrency prices today: Bitcoin Ethereum, BNB recover after up to 8% plunge"));
        articles.add(new Article("Phil Blanche", "Scotland-Ukrainer postponed until June but Wales semi-final goes ahead as planned"));
        articles.add(new Article("NYT News Service", "BiTCoin was made for this moment. So why isn't it booming?"));
        articles.add(new Article("Reuters", "BITCOIN rallies after Biden signs executive order on digital assets"));
        articles.add(new Article("Henry Saker-Clark", "Omicron cuts into sales momentum at Zara owner Inditex"));
        articles.add(new Article("Medha Singh", "Cryptoverse: bitcoin's scared of commitment, Mr Biden"));
        articles.add(new Article("AP news wire", "Shiffrin 5th after Olympic combined downhill; has medal shot"));
        return articles;
    }

}
