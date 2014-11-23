package realtimeweb.twittersearch.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;






import realtimeweb.twittersearch.domain.Url;
import realtimeweb.twittersearch.domain.Media;
import realtimeweb.twittersearch.domain.Hashtag;

/**
 * 
 */
public class Entities {
	
    private ArrayList<Media> media;
    private ArrayList<Hashtag> hashtags;
    private ArrayList<Url> urls;
    
    
    /*
     * @return 
     */
    public ArrayList<Media> getMedia() {
        return this.media;
    }
    
    /*
     * @param 
     * @return ArrayList<Media>
     */
    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }
    
    /*
     * @return 
     */
    public ArrayList<Hashtag> getHashtags() {
        return this.hashtags;
    }
    
    /*
     * @param 
     * @return ArrayList<Hashtag>
     */
    public void setHashtags(ArrayList<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }
    
    /*
     * @return 
     */
    public ArrayList<Url> getUrls() {
        return this.urls;
    }
    
    /*
     * @param 
     * @return ArrayList<Url>
     */
    public void setUrls(ArrayList<Url> urls) {
        this.urls = urls;
    }
    
	
	/**
	 * Creates a string based representation of this Entities.
	
	 * @return String
	 */
	public String toString() {
		return "Entities[" +media+", "+hashtags+", "+urls+"]";
	}
	
	/**
	 * Internal constructor to create a Entities from a json representation.
	 * @param map The raw json data that will be parsed.
	 * @return 
	 */
    public Entities(Map<String, Object> raw) {
        // TODO: Check that the data has the correct schema.
        // NOTE: It's much safer to check the Map for fields than to catch a runtime exception.
        try {
        	if(raw.get("media")!=null){
        		 this.media = new ArrayList<Media>();
                 Iterator<Object> mediaIter = ((List<Object>)raw.get("media")).iterator();
                 while (mediaIter.hasNext()) {
                     this.media.add(new Media((Map<String, Object>)mediaIter.next()));
                 }
        	}
            this.hashtags = new ArrayList<Hashtag>();
            Iterator<Object> hashtagsIter = ((List<Object>)raw.get("hashtags")).iterator();
            while (hashtagsIter.hasNext()) {
                this.hashtags.add(new Hashtag((Map<String, Object>)hashtagsIter.next()));
            }
            this.urls = new ArrayList<Url>();
            Iterator<Object> urlsIter = ((List<Object>)raw.get("urls")).iterator();
            while (urlsIter.hasNext()) {
                this.urls.add(new Url((Map<String, Object>)urlsIter.next()));
            }
        } catch (NullPointerException e) {
    		System.err.println("Could not convert the response to a Entities; a field was missing.");
    		e.printStackTrace();
    	} catch (ClassCastException e) {
    		System.err.println("Could not convert the response to a Entities; a field had the wrong structure.");
    		e.printStackTrace();
        }
    
	}	
}