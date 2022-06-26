package at.ac.fhcampuswien.filterStructuralPattern;

import at.ac.fhcampuswien.Article;

import java.util.List;

//Structural Pattern we use for bonus point
//we chose Pattern FILTER because of all the Streams we had to use in Exercise 3
//basically all we do in all those Stream Method from Exercise 3 is to filter certain aspects out of List of Article Object
//this pattern is fitted for filtern the same List of Objects over an over again but with different criteria
//--> which is exactly what we do with all those Streams from Exercise 3,
//so we decided to use this pattern to structure our code better and reduce some amount of code in HelloController

//create public Interface with the name Criteria
public interface Criteria {
    //add one method called criteria which returns a List of Articles Objects and has a List of Article Objects as param
    //so all we do is alter List and return altered List
    List<Article> criteria (List<Article> articleList);
}
