package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.List;

public interface Criteria {
    public List<Article> criteria (List<Article> articleList);
}
