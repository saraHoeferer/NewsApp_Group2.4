package at.ac.fhcampuswien;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

//class from Solution of Exercise 3 from Leons Github only made one small change due to errors with fileName
public abstract class Downloader {

    public static final String HTML_EXTENSION = ".html";
    public static final String DIRECTORY_DOWNLOAD = "./download/";

    public abstract int process(List<String> urls) throws NewsApiExceptions;

    public String saveUrl2File(String urlString) throws NewsApiExceptions {
        InputStream is = null;
        OutputStream os = null;
        String fileName;
        try {
            URL url4download = new URL(urlString);  // Convert string to URL
            is = url4download.openStream();
            fileName = urlString.substring(urlString.lastIndexOf('/') + 1); // extract filename

            if (fileName.isEmpty()) {
                fileName = url4download.getHost() + HTML_EXTENSION; // if no filename could be extracted use the URL host and .html extension
            } else {
                //if filename is not empty then replace all ? with "" --> because a lot of errors because ? is not allowed in fileNames
                fileName = fileName.replace("?", "");
            }

            os = new FileOutputStream(DIRECTORY_DOWNLOAD + fileName);   // write to /download/<filename>

            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {   // read byte for byte and write it to file
                os.write(b, 0, length);
            }
        } catch (MalformedURLException e){
            throw new NewsApiExceptions("Cannot convert " + urlString + " to URL. Error message: " + e.getMessage());
        } catch (IOException e) {
            throw new NewsApiExceptions("Either failed to open URL: " + urlString + " or failed to write to Folder. Error message: " + e.getMessage());
        } finally {
            try {
                if(is != null)
                    is.close();
                if(os != null)
                    os.close();
            } catch (IOException | NullPointerException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }
}