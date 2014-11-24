package realtimeweb.twittersearch.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




/**
 * hashtags extracted from the Tweet text
 */
public class Hashtag {
	
    private String text;
    
    
    /*
     * @return 
     */
    public String getText() {
        return this.text;
    }
    
    /*
     * @param 
     * @return String
     */
    public void setText(String text) {
        this.text = text;
    }
    
	
	/**
	 * Creates a string based representation of this Hashtag.
	
	 * @return String
	 */
	public String toString() {
		return "Hashtag[" +text+"]";
	}
	
	/**
	 * Internal constructor to create a Hashtag from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public Hashtag(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
            this.text = raw.get("text").toString();
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Hashtag; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Hashtag; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}