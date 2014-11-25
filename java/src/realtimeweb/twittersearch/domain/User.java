package realtimeweb.twittersearch.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




/**
 * The Twitter user who post this tweet
 */
public class User {
	
    private String name;
    private String screen_Name;
    
    
    /*
     * @return The name of the user, as they ve defined it. Not necessarily a person s name.
     */
    public String getName() {
        return this.name;
    }
    
    /*
     * @param The name of the user, as they ve defined it. Not necessarily a person s name.
     * @return String
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /*
     * @return The screen name, handle, or alias that this user identifies themselves with.
     */
    public String getScreen_Name() {
        return this.screen_Name;
    }
    
    /*
     * @param The screen name, handle, or alias that this user identifies themselves with.
     * @return String
     */
    public void setScreen_Name(String screen_Name) {
        this.screen_Name = screen_Name;
    }
    
	
	/**
	 * Creates a string based representation of this User.
	
	 * @return String
	 */
	public String toString() {
		return "User[" +name+", "+screen_Name+"]";
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
            this.screen_Name = raw.get("name").toString();
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a User; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a User; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}