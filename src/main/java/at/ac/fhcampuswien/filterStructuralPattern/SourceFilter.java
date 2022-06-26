package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SourceFilter implements Criteria{
    @Override
    public List<Article> criteria (List<Article> articleList){
        Stream<Article> streamofArticle = articleList.stream();
        //filter articles according to source and count them
        List<Article> source = streamofArticle
                .filter(article -> article.getSource().getName().equals("New York Times"))
                .collect(Collectors.toList());
        return source;
    }
}
