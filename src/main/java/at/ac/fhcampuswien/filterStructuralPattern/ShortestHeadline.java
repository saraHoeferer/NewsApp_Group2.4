package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class to filter all headlines which less than 60 characters-> implements Criteria Interface
public class ShortestHeadline implements Criteria {
    //Override public function criteria
    @Override
    public List<Article> criteria (List<Article> articleList){
        Stream<Article> streamFromList = articleList.stream();
        //filter for all articles which titel have less than 60 characters
        List<Article> shortest = streamFromList
                .filter(article -> article.getTitle().length() < 60)
                //collect all them
                .collect(Collectors.toList());
        //return the list of all articles with less than 60 characters in their headline
        return shortest;
    }
}
