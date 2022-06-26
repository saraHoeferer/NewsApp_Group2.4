package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class mostArticles implements Criteria{
    @Override
    public List<Article> criteria (List<Article> articleList){
        String mostCommonSource = articleList.stream()
                .flatMap(Article->Stream.of(Article.getSource().getName()))
                //collect occurence of all sources
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                //compare the entries and select the one with the most occurence
                .entrySet().stream().max(Map.Entry.comparingByValue())
                //make it values of string
                .map(Map.Entry::getKey).orElse(null);

        List<Article> mostCommon = articleList.stream()
                .filter(article -> article.getSource().getName().equals(mostCommonSource))
                .collect(Collectors.toList());

        return mostCommon;
    }
}
