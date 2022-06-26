package at.ac.fhcampuswien;

import java.net.MalformedURLException;
import java.net.URL;

//Own Builder class --> URL Builder to build url for NewsApi Requests
public class URLBuilder {
    //instance variables
    //scheme for Request eg. http
    private String scheme;
    //host for Request eg. newsapi.org
    private String host;
    //path for Request eg. v2/endpoint
    private String pathSegment;
    //queries for Request eg. q=bitcoin
    private String queryParamater;

    //set scheme
    public URLBuilder scheme (String scheme){
        //add string + ://
        this.scheme = scheme +  "://";
        //return it
        return this;
    }

    //set host
    public URLBuilder host (String host){
        //add string + /
        this.host = host + "/";
        //return it
        return this;
    }

    //set path
    public URLBuilder pathSegment (String pathSegment){
        //add string
        this.pathSegment = pathSegment;
        //return it
        return this;
    }

    //set query/queries
    public URLBuilder queryParameter (String queryName, String query){
        //if not query is existent
        if (this.queryParamater == null){
            //add ? at the beginning and then queryName=query
            this.queryParamater = "?"+queryName+"="+query;
        } else {
            //if there is already another query add & at beginning and extend query String
            this.queryParamater += "&" + queryName + "=" + query;
        }
        return this;
    }

    @Override
    //put all into one string
    public String toString(){
        return this.scheme + this.host + this.pathSegment + this.queryParamater;
    }

    //build new URL according to String
    public URL build() throws MalformedURLException {
        //throw exception if URL is malformed
        return new URL(this.toString());
    }
}
