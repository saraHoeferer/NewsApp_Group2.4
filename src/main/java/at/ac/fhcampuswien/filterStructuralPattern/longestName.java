package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class longestName implements Criteria{
    @Override
    public List<Article> criteria (List<Article> articleList){
        Comparator<Article> compByLength = (article1, article2) -> article1.getAuthor().length() - article2.getAuthor().length();
        List<Article> mostCommon = articleList.stream()
                //compare author name length and save the one with the most characters
                .max(compByLength).stream().collect(Collectors.toList());
        return mostCommon;
    }
}
