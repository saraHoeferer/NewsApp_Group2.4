package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class to filter list to see how many articles where published by the New York Times -> implements Criteria Interface
public class SourceFilter implements Criteria{
    //Override public function criteria
    @Override
    public List<Article> criteria (List<Article> articleList){
        Stream<Article> streamofArticle = articleList.stream();
        //filter articles according to source
        List<Article> source = streamofArticle
                .filter(article -> article.getSource().getName().equals("New York Times"))
                //collect them in new list
                .collect(Collectors.toList());
        //return list of all articles published by the New York Times
        return source;
    }
}
