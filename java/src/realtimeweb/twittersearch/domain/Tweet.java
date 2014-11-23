package realtimeweb.twittersearch.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;



import realtimeweb.twittersearch.domain.Entities;
import realtimeweb.twittersearch.domain.User;

/**
 * 
 */
public class Tweet {
	
    private Boolean favorited;
    private String text;
    private String created_At;
    private ArrayList<Double> coordinates;
    private Entities entities;
    private User user;
    private Integer retweet_Count;
    private Integer favorite_Count;
    private String id_Str;
    
    
    /*
     * @return 
     */
    public Boolean getFavorited() {
        return this.favorited;
    }
    
    /*
     * @param 
     * @return Boolean
     */
    public void setFavorited(Boolean favorited) {
        this.favorited = favorited;
    }
    
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
    
    /*
     * @return 
     */
    public String getCreated_At() {
        return this.created_At;
    }
    
    /*
     * @param 
     * @return String
     */
    public void setCreated_At(String created_At) {
        this.created_At = created_At;
    }
    
    /*
     * @return 
     */
    public ArrayList<Double> getCoordinates() {
        return this.coordinates;
    }
    
    /*
     * @param 
     * @return ArrayList<Double>
     */
    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
    
    /*
     * @return 
     */
    public Entities getEntities() {
        return this.entities;
    }
    
    /*
     * @param 
     * @return Entities
     */
    public void setEntities(Entities entities) {
        this.entities = entities;
    }
    
    /*
     * @return 
     */
    public User getUser() {
        return this.user;
    }
    
    /*
     * @param 
     * @return User
     */
    public void setUser(User user) {
        this.user = user;
    }
    
    /*
     * @return 
     */
    public Integer getRetweet_Count() {
        return this.retweet_Count;
    }
    
    /*
     * @param 
     * @return Integer
     */
    public void setRetweet_Count(Integer retweet_Count) {
        this.retweet_Count = retweet_Count;
    }
    
    /*
     * @return 
     */
    public Integer getFavorite_Count() {
        return this.favorite_Count;
    }
    
    /*
     * @param 
     * @return Integer
     */
    public void setFavorite_Count(Integer favorite_Count) {
        this.favorite_Count = favorite_Count;
    }
    
    /*
     * @return 
     */
    public String getId_Str() {
        return this.id_Str;
    }
    
    /*
     * @param 
     * @return String
     */
    public void setId_Str(String id_Str) {
        this.id_Str = id_Str;
    }
    
	
	/**
	 * Creates a string based representation of this Tweet.
	
	 * @return String
	 */
	public String toString() {
		return "Tweet[" +favorited+", "+text+", "+created_At+", "+coordinates+", "+entities+", "+user+", "+retweet_Count+", "+favorite_Count+", "+id_Str+"]";
	}
	
	/**
	 * Internal constructor to create a Tweet from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public Tweet(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
            this.favorited = Boolean.parseBoolean(raw.get("id_str").toString());
            this.text = raw.get("text").toString();
            this.created_At = raw.get("created_at").toString();
           
            if(raw.get("coordinates")!=null){
            	 this.coordinates = new ArrayList<Double>();
            	 Iterator<Object> coordinatesIter = ((List<Object>)raw.get("coordinates")).iterator();
                 while (coordinatesIter.hasNext()) {
                     this.coordinates.add(new Double((Double)coordinatesIter.next()));
                 }
            }
           
            this.entities = new Entities((Map<String, Object>)raw.get("entities"));
            this.user = new User((Map<String, Object>)raw.get("user"));
            this.retweet_Count = Integer.parseInt(raw.get("retweet_count").toString());
            this.favorite_Count = Integer.parseInt(raw.get("favorite_count").toString());
            this.id_Str = raw.get("id_str").toString();
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Tweet; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Tweet; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}