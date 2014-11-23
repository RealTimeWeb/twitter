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
public class Media {
	
    private String type;
    private String media_Url;
    
    
    /*
     * @return 
     */
    public String getType() {
        return this.type;
    }
    
    /*
     * @param 
     * @return String
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /*
     * @return 
     */
    public String getMedia_Url() {
        return this.media_Url;
    }
    
    /*
     * @param 
     * @return String
     */
    public void setMedia_Url(String media_Url) {
        this.media_Url = media_Url;
    }
    
	
	/**
	 * Creates a string based representation of this Media.
	
	 * @return String
	 */
	public String toString() {
		return "Media[" +type+", "+media_Url+"]";
	}
	
	/**
	 * Internal constructor to create a Media from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public Media(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
            this.type = raw.get("type").toString();
            this.media_Url = raw.get("media_url").toString();
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Media; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Media; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}