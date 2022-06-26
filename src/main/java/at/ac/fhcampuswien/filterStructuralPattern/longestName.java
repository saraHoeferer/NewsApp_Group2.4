package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//class to filter author with the longest name -> implements Criteria Interface
public class longestName implements Criteria{
    //Override public function criteria
    @Override
    public List<Article> criteria (List<Article> articleList){
        //write Comparator which compared to length of Author names of two articles
        Comparator<Article> compByLength = (article1, article2) -> article1.getAuthor().length() - article2.getAuthor().length();
        List<Article> longest = articleList.stream()
                //compare author name length and collect them into list
                .max(compByLength).stream().collect(Collectors.toList());
        //return list of articles with author with the longest name
        return longest;
    }
}
