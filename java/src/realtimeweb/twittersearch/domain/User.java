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
public class User {
	
    private String name;
    private String profile_Image_Url;
    
    
    /*
     * @return 
     */
    public String getName() {
        return this.name;
    }
    
    /*
     * @param 
     * @return String
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /*
     * @return 
     */
    public String getProfile_Image_Url() {
        return this.profile_Image_Url;
    }
    
    /*
     * @param 
     * @return String
     */
    public void setProfile_Image_Url(String profile_Image_Url) {
        this.profile_Image_Url = profile_Image_Url;
    }
    
	
	/**
	 * Creates a string based representation of this User.
	
	 * @return String
	 */
	public String toString() {
		return "User[" +name+", "+profile_Image_Url+"]";
	}
	
	/**
	 * Internal constructor to create a User from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public User(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
            this.name = raw.get("name").toString();
            this.profile_Image_Url = raw.get("profile_image_url").toString();
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a User; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a User; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}