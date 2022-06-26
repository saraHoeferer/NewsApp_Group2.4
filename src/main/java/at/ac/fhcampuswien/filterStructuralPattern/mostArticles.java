package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class to filter which source is most stated in list of articles-> implements Criteria Interface
public class mostArticles implements Criteria{
    //Override public function criteria
    @Override
    public List<Article> criteria (List<Article> articleList){
        //make stream to get Name of most common source
        String mostCommonSource = articleList.stream()
                .flatMap(Article->Stream.of(Article.getSource().getName()))
                //collect occurence of all sources
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                //compare the entries and select the one with the most occurence
                .entrySet().stream().max(Map.Entry.comparingByValue())
                //make it values of string
                .map(Map.Entry::getKey).orElse(null);

        //Filter list according to whatever source is most common
        List<Article> mostCommon = articleList.stream()
                .filter(article -> article.getSource().getName().equals(mostCommonSource))
                .collect(Collectors.toList());

        //return list of all articles which are from most common source
        return mostCommon;
    }
}
