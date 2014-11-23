package realtimeweb.twittersearch.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




/**
 * 
 */
public class Url {
	
    private String url;
    
    
    /*
     * @return 
     */
    public String getUrl() {
        return this.url;
    }
    
    /*
     * @param 
     * @return String
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
	
	/**
	 * Creates a string based representation of this Url.
	
	 * @return String
	 */
	public String toString() {
		return "Url[" +url+"]";
	}
	
	/**
	 * Internal constructor to create a Url from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public Url(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
            this.url = raw.get("url").toString();
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Url; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Url; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}