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
 * Entities provide structured data from Tweets including resolved URLs, media, hashtags without having to parse the text to extract that information.
 */
public class Entities {
	
    private ArrayList<Media> media;
    private ArrayList<Hashtag> hashtags;
    private ArrayList<Url> urls;
    
    
    /*
     * @return An array of media attached to the Tweet with the Twitter Photo Upload feature.
     */
    public ArrayList<Media> getMedia() {
        return this.media;
    }
    
    /*
     * @param An array of media attached to the Tweet with the Twitter Photo Upload feature.
     * @return ArrayList<Media>
     */
    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }
    
    /*
     * @return An array of hashtags extracted from the Tweet text.
     */
    public ArrayList<Hashtag> getHashtags() {
        return this.hashtags;
    }
    
    /*
     * @param An array of hashtags extracted from the Tweet text.
     * @return ArrayList<Hashtag>
     */
    public void setHashtags(ArrayList<Hashtag> hashtags) {
        this.hashtags = hashtags;
    }
    
    /*
     * @return An array of URLs extracted from the Tweet text.
     */
    public ArrayList<Url> getUrls() {
        return this.urls;
    }
    
    /*
     * @param An array of URLs extracted from the Tweet text.
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
            this.media = new ArrayList<Media>();
            //modified: check for null
            if(raw.get("media")!=null){
            Iterator<Object> mediaIter = ((List<Object>)raw.get("media")).iterator();
            while (mediaIter.hasNext()) {
            	//modified: cast to Map
                this.media.add(new Media((Map<String, Object>)mediaIter.next()));
            }
            }
            this.hashtags = new ArrayList<Hashtag>();
            Iterator<Object> hashtagsIter = ((List<Object>)raw.get("hashtags")).iterator();
            while (hashtagsIter.hasNext()) {
            	//modified: cast to Map
                this.hashtags.add(new Hashtag((Map<String, Object>)hashtagsIter.next()));
            }
            this.urls = new ArrayList<Url>();
            Iterator<Object> urlsIter = ((List<Object>)raw.get("urls")).iterator();
            while (urlsIter.hasNext()) {
            	//modified: cast to Map
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