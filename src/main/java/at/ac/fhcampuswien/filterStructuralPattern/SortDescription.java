package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//class to sort list according to length of description -> implements Criteria Interface
public class SortDescription implements Criteria {
    //Override public function criteria
    @Override
    public List<Article> criteria (List<Article> articleList){
        //make stream of article list
        Stream<Article> streamOfArticles = articleList.stream();
        //sort them
        List<Article> sorted = streamOfArticles
                .sorted((a1, a2) -> {
                    //if first description is null
                    if (a1.getDescription() == null) {
                        //second one is bigger
                        return -1;
                        //if second description is null
                    } else if (a2.getDescription() == null) {
                        //first one is bigger
                        return 1;
                        //if none of them are null
                    } else {
                        //if they have the same length
                        if (a1.getDescription().length() == a2.getDescription().length())
                            //compare alphabetically
                            return a1.getDescription().compareTo(a2.getDescription());
                            //if first one is bigger
                        else if (a1.getDescription().length() > a2.getDescription().length())
                            return 1;
                            //else second one is bigger
                        else return -1;
                    }
                })
                //collect them all after sorting
                .collect(Collectors.toList());
        //return list of articles sorted after length of description
        return sorted;
    }
}
