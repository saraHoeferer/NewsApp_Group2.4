package at.ac.fhcampuswien;

import java.net.MalformedURLException;
import java.net.URL;

public class URLBuilder {
    private String scheme;
    private String host;
    private String pathSegment;
    private String queryParamater;

    public URLBuilder scheme (String scheme){
        this.scheme = scheme +  "://";
        return this;
    }

    public URLBuilder host (String host){
        this.host = host + "/";
        return this;
    }

    public URLBuilder pathSegment (String pathSegment){
        this.pathSegment = pathSegment;
        return this;
    }

    public URLBuilder queryParameter (String queryName, String query){
        if (this.queryParamater == null){
            this.queryParamater = "?"+queryName+"="+query;
        } else {
            this.queryParamater += "&" + queryName + "=" + query;
        }
        return this;
    }

    @Override
    public String toString(){
        return this.scheme + this.host + this.pathSegment + this.queryParamater;
    }
    public URL build() throws MalformedURLException {
        return new URL(this.toString());
    }
}
