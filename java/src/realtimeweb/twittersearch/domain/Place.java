package realtimeweb.twittersearch.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




/**
 * Places are specific, named locations with corresponding geo coordinates.
 */
public class Place {
	
    private String country;
    private String full_Name;
    
    
    /*
     * @return Name of the country containing this place.
     */
    public String getCountry() {
        return this.country;
    }
    
    /*
     * @param Name of the country containing this place.
     * @return String
     */
    public void setCountry(String country) {
        this.country = country;
    }
    
    /*
     * @return Full human-readable representation of the place s name.
     */
    public String getFull_Name() {
        return this.full_Name;
    }
    
    /*
     * @param Full human-readable representation of the place s name.
     * @return String
     */
    public void setFull_Name(String full_Name) {
        this.full_Name = full_Name;
    }
    
	
	/**
	 * Creates a string based representation of this Place.
	
	 * @return String
	 */
	public String toString() {
		return "Place[" +country+", "+full_Name+"]";
	}
	
	/**
	 * Internal constructor to create a Place from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public Place(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
        	//modified: handle null
        	if(raw != null){
	            this.country = raw.get("country").toString();
	            this.full_Name = raw.get("full_name").toString();
        	}else{
        		this.country = "";
        		this.full_Name = "";
        	}
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Place; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Place; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}